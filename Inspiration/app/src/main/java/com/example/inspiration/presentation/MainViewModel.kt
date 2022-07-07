package com.example.inspiration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.repository.AccessTokenRepositoryImpl
import com.example.inspiration.data.repository.UserVerificationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userVerificationRepositoryImpl: UserVerificationRepositoryImpl,
    private val accessTokenRepositoryImpl: AccessTokenRepositoryImpl
): ViewModel() {

    private val verificationMutStateFlow = MutableStateFlow<UserVerification?>(null)
    val verificationStateFlow: StateFlow<UserVerification?> = verificationMutStateFlow

    private val accessTokenMutStateFlow = MutableStateFlow<String?>(null)
    val accessTokenStateFlow: StateFlow<String?> = accessTokenMutStateFlow

    init {
        viewModelScope.launch {

            getUserVerification()
                .onEach {
                    verificationMutStateFlow.update { it }
                }
                .launchIn(viewModelScope)

            getAccessToken()
                .onEach {
                    accessTokenMutStateFlow.update { it }
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