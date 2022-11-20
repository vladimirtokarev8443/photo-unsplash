package com.example.domain.usecase.verification

import com.example.domain.repository.VerificationRepository
import javax.inject.Inject

class SaveVerificationStatusUseCase@Inject constructor(
    private val verificationRepository: VerificationRepository
) {
    suspend fun execute(status: String) = verificationRepository.saveVerificationStatus(status)
}