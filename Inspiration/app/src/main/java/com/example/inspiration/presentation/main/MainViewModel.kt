package com.example.inspiration.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.network.auth.AccessToken
import com.example.inspiration.data.repository.AccessTokenRepositoryImpl
import com.example.inspiration.data.repository.UserVerificationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userVerificationRepositoryImpl: UserVerificationRepositoryImpl,
    private val accessTokenRepositoryImpl: AccessTokenRepositoryImpl
): ViewModel() {

    private val verificationMutStateFlow = MutableStateFlow<UserVerification?>(null)
    val verificationStateFlow: StateFlow<UserVerification?> = verificationMutStateFlow

    init {
        viewModelScope.launch {

            getUserVerification()
                .onEach {
                    verificationMutStateFlow.value = it
                }
                .launchIn(viewModelScope)

            getAccessToken()
                .onEach {
                    Timber.d(it)
                    AccessToken.accessToken = it

                }
                .launchIn(viewModelScope)
        }

    }

    private suspend fun getUserVerification(): Flow<UserVerification>{
        return userVerificationRepositoryImpl.getUserVerification()
    }

    private suspend fun getAccessToken(): Flow<String?>{
        return accessTokenRepositoryImpl.getAccessToken()
    }

}