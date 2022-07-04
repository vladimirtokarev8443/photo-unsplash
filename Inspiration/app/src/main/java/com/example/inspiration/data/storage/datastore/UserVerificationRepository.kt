package com.example.inspiration.data.storage.datastore

import com.example.inspiration.data.models.UserVerification
import kotlinx.coroutines.flow.Flow

interface UserVerificationRepository {

    suspend fun saveUserVerification(userVerification: UserVerification)

    suspend fun getUserVerification(): Flow<UserVerification>

}