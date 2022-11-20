package com.example.inspiration.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enum.VerificationStatus
import com.example.domain.usecase.verification.GetAccessTokenUseCase
import com.example.domain.usecase.verification.GetVerificationStatusUseCase
import com.example.data.repository.AccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVerificationStatusUseCase: GetVerificationStatusUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val job = SupervisorJob()
    private val verificationMutStateFlow = MutableStateFlow<String?>(null)
    val verificationStateFlow: StateFlow<String?> = verificationMutStateFlow

    init {
        viewModelScope.launch(job) {
            getVerificationStatus()
            getAccessToken()
        }
    }

    private suspend fun getVerificationStatus() = withContext(Dispatchers.IO){
        getVerificationStatusUseCase.execute()
            .onEach {
                verificationMutStateFlow.value = it ?: VerificationStatus.FIRST_VIZIT.name
            }
            .launchIn(viewModelScope)
    }

    private suspend fun getAccessToken() = withContext(Dispatchers.IO){
        getAccessTokenUseCase.execute()
            .onEach {
                AccessToken.token = it
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        //job.cancel()
    }

}