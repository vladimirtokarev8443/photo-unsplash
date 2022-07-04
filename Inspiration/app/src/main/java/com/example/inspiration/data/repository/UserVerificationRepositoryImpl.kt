package com.example.inspiration.data.repository

import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.storage.datastore.UserVerificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserVerificationRepositoryImpl @Inject constructor(
    private val userVerificationRepository: UserVerificationRepository
    ) {

    suspend fun saveUserVerification(userVerification: UserVerification){
        userVerificationRepository.saveUserVerification(userVerification = userVerification)
    }

    suspend fun getUserVerification(): Flow<UserVerification>{
        return userVerificationRepository.getUserVerification()
    }
}