package com.example.inspiration.data.repository

import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.storage.datastore.UserVerificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserVerificationRepositoryImpl @Inject constructor(
    private val userVerificationRepository: UserVerificationRepository
    ) {

    suspend fun saveUserVerification(userVerification: UserVerification) = withContext(Dispatchers.IO){
        userVerificationRepository.saveUserVerification(userVerification = userVerification)
    }

    suspend fun getUserVerification(): Flow<UserVerification> = withContext(Dispatchers.IO){
        return@withContext userVerificationRepository.getUserVerification()
    }
}