package com.example.inspiration.di.data

import android.app.Application
import com.example.data.api.UnsplashApi
import com.example.data.repository.PhotoRepositoryImpl
import com.example.data.repository.VerificationRepositoryImpl
import com.example.data.room.PhotoDao
import com.example.domain.repository.PhotoRepository
import com.example.domain.repository.VerificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import net.openid.appauth.AuthorizationService

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun providesAuthorizationService(application: Application): AuthorizationService {
        return AuthorizationService(application)
    }

    @Provides
    fun providesVerificationRepositoryImpl(dataStore: com.example.data.storage.StorageDataStore): VerificationRepository {
        return VerificationRepositoryImpl(dataStore)
    }

    @Provides
    fun providesPhotoRepositoryImpl(api: UnsplashApi, photoDao: PhotoDao): PhotoRepository {
        return PhotoRepositoryImpl(api, photoDao)
    }

}