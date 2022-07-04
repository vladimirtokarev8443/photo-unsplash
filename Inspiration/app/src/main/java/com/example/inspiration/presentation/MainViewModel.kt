package com.example.inspiration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inspiration.R
import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.repository.UserVerificationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userVerificationRepository: UserVerificationRepositoryImpl
): ViewModel() {

    private val verificationMutStateFlow = MutableStateFlow<UserVerification?>(null)
    val verification: StateFlow<UserVerification?> = verificationMutStateFlow

    init {
        viewModelScope.launch {
            getUserVerification()
                .onEach {
                    verificationMutStateFlow.update { it }
                }
                .launchIn(viewModelScope)
        }

    }

    private suspend fun getUserVerification(): Flow<UserVerification>{
        return userVerificationRepository.getUserVerification()
    }

     suspend fun saveUserVerification(userVerification: UserVerification){

        userVerificationRepository.saveUserVerification(userVerification = userVerification)
    }
}