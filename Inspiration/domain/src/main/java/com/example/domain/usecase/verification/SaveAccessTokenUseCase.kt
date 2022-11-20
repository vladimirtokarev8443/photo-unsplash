package com.example.domain.usecase.verification

import com.example.domain.repository.VerificationRepository
import javax.inject.Inject


class SaveAccessTokenUseCase @Inject constructor(
    private val verificationRepository: VerificationRepository
) {
    suspend fun execute(token: String) = verificationRepository.saveAccessToken(token)
}