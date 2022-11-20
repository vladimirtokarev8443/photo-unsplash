package com.example.domain.usecase.verification

import com.example.domain.repository.VerificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenUseCase@Inject constructor(
    private val verificationRepository: VerificationRepository
) {
    suspend fun execute(): Flow<String?> = verificationRepository.getAccessToken()
}