package com.example.inspiration.di.data

import android.app.Application
import android.content.Context
import com.example.inspiration.data.repository.AccessTokenRepositoryImpl
import com.example.inspiration.data.repository.AuthRepository
import com.example.inspiration.data.repository.UserVerificationRepositoryImpl
import com.example.inspiration.data.storage.datastore.AccessTokenRepository
import com.example.inspiration.data.storage.datastore.StorageDataStore
import com.example.inspiration.data.storage.datastore.UserVerificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import net.openid.appauth.AuthorizationService
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModul {

    @Provides
    fun providesUserVerificationImpl(
        storageDataStore: StorageDataStore
    ): UserVerificationRepositoryImpl{
        return UserVerificationRepositoryImpl(userVerificationRepository = storageDataStore)
    }

    @Provides
    fun providesAccessTokenRepositoryImpl(
        storageDataStore: StorageDataStore
    ): AccessTokenRepositoryImpl {
        return AccessTokenRepositoryImpl(accessTokenRepository = storageDataStore)
    }

    @Provides
    fun providesAuthRepository(): AuthRepository {
        return AuthRepository()
    }

    @Provides
    fun providesAuthorizationService(application: Application): AuthorizationService {
        return AuthorizationService(application)
    }
}