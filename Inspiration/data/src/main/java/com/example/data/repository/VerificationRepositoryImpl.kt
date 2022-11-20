package com.example.data.repository

import com.example.data.storage.ACCESS_TOKEN
import com.example.data.storage.StorageDataStore
import com.example.data.storage.VERIFICATION_STATUS
import com.example.domain.repository.VerificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val dataStore: StorageDataStore
): VerificationRepository {

    override suspend fun getAccessToken(): Flow<String?> = dataStore.getValueString(ACCESS_TOKEN)

    override suspend fun saveAccessToken(token: String) {
        dataStore.saveValueString(token, ACCESS_TOKEN)
    }

    override suspend fun getVerificationStatus(): Flow<String?> = dataStore.getValueString(
        VERIFICATION_STATUS)

    override suspend fun saveVerificationStatus(status: String) {
        dataStore.saveValueString(status, VERIFICATION_STATUS)
    }
}