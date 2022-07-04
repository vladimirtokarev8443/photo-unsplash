package com.example.inspiration.data.storage.datastore

import kotlinx.coroutines.flow.Flow

interface AccessTokenRepository {

    suspend fun saveAccessToken(accessToken: String)

    suspend fun getAccessToken(): Flow<String?>
}