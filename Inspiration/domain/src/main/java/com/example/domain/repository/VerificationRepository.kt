package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface VerificationRepository {

    suspend fun getAccessToken(): Flow<String?>

    suspend fun saveAccessToken(token: String)

    suspend fun getVerificationStatus(): Flow<String?>

    suspend fun saveVerificationStatus(status: String)
}