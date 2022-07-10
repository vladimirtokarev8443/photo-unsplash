package com.example.inspiration.presentation.auth

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inspiration.R
import com.example.inspiration.SingleLiveEvent
import com.example.inspiration.data.enum.Verification
import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.repository.AccessTokenRepositoryImpl
import com.example.inspiration.data.repository.AuthRepository
import com.example.inspiration.data.repository.UserVerificationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authService: AuthorizationService,
    private val accessTokenRepositoryImpl: AccessTokenRepositoryImpl,
    private val userVerificationRepositoryImpl: UserVerificationRepositoryImpl
): ViewModel() {

    private val openAuthPageLiveEvent = SingleLiveEvent<Intent>()
    val openAuthPageLiveData: LiveData<Intent>
        get() = openAuthPageLiveEvent

    private val toastLiveEvent = SingleLiveEvent<Int>()
    val toastLiveData: LiveData<Int>
        get() = toastLiveEvent

    private val loadingMutableLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    private val authSuccessLiveEvent = SingleLiveEvent<Unit>()
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

        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = { accessToken ->
                saveAccessToken(accessToken = accessToken)
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
            authRepository.getAuthRequest(),
            customTabsIntent
        )

        openAuthPageLiveEvent.postValue(openAuthPageIntent)
    }

    private fun saveAccessToken(accessToken: String){
        viewModelScope.launch {
            accessTokenRepositoryImpl.saveAccessToken(accessToken = accessToken)
        }
    }

    fun saveUserVerification(){
        viewModelScope.launch {
            userVerificationRepositoryImpl.saveUserVerification(
                UserVerification(verificationValue = Verification.VERIFIED)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}