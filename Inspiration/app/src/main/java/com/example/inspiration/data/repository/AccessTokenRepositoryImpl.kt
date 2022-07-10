package com.example.inspiration.data.repository

import com.example.inspiration.data.storage.datastore.AccessTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccessTokenRepositoryImpl @Inject constructor(
    private val accessTokenRepository: AccessTokenRepository
) {

    suspend fun saveAccessToken(accessToken: String){
        accessTokenRepository.saveAccessToken(accessToken = accessToken)
    }

    suspend fun getAccessToken(): Flow<String?>{
        return accessTokenRepository.getAccessToken()
    }
}