package com.example.inspiration.presentation.auth

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AuthConfig
import com.example.domain.enum.VerificationStatus
import com.example.domain.usecase.verification.SaveAccessTokenUseCase
import com.example.domain.usecase.verification.SaveVerificationStatusUseCase
import com.example.inspiration.R
import com.example.inspiration.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.openid.appauth.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthorizationService,
    private val saveVerificationStatusUseCase: SaveVerificationStatusUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
): ViewModel() {

    private val openAuthPageLiveEvent =
        SingleLiveEvent<Intent>()
    val openAuthPageLiveData: LiveData<Intent>
        get() = openAuthPageLiveEvent

    private val toastLiveEvent =
        SingleLiveEvent<Int>()
    val toastLiveData: LiveData<Int>
        get() = toastLiveEvent

    private val loadingMutableLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    private val authSuccessLiveEvent =
        SingleLiveEvent<Unit>()
    val authSuccessLiveData: LiveData<Unit>
        get() = authSuccessLiveEvent

    fun handleAuthResponseIntent(intent: Intent) {

        val exception = AuthorizationException.fromIntent(intent)

        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {

            exception != null -> onAuthCodeFailed(exception)

            tokenExchangeRequest != null ->
                onAuthCodeReceived(tokenExchangeRequest)
        }
    }

    private fun onAuthCodeFailed(exception: AuthorizationException) {
        //добавить ошибку
        toastLiveEvent.postValue(R.string.auth_canceled)
    }

    private fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        loadingMutableLiveData.postValue(true)

        performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = { accessToken ->

                saveAccessToken(accessToken = accessToken)
                saveVerificationStatus()
                loadingMutableLiveData.postValue(false)
                authSuccessLiveEvent.postValue(Unit)
            },
            onError = {
                loadingMutableLiveData.postValue(false)
                toastLiveEvent.postValue(R.string.auth_canceled)
            }
        )
    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder()
            //.setToolbarColor(ContextCompat.getColor(getApplication(), R.color.black))
            .build()

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            getAuthRequest(),
            customTabsIntent
        )

        openAuthPageLiveEvent.postValue(openAuthPageIntent)
    }

    private fun saveAccessToken(accessToken: String){
        viewModelScope.launch {
            saveAccessTokenUseCase.execute(accessToken)
        }
    }

    fun saveVerificationStatus(){
        viewModelScope.launch {
            saveVerificationStatusUseCase.execute(VerificationStatus.VERIFIED.name)
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }

    private fun getAuthRequest(): AuthorizationRequest {
        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(AuthConfig.AUTH_URI),
            Uri.parse(AuthConfig.TOKEN_URI)
        )

        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setScope(AuthConfig.SCOPE_PUBLIC)
            .setCodeVerifier(null)
            .build()
    }

    fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        onComplete: (String) -> Unit,
        onError: () -> Unit
    ) {
        authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, ex ->
            when {
                response != null -> {
                    val accessToken = response.accessToken.orEmpty()
                    //AccessToken.accessToken = accessToken

                    onComplete(accessToken)
                }
                else -> onError()
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(AuthConfig.CLIENT_SECRET)
    }
}