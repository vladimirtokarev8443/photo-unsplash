package com.example.inspiration.di

import android.content.Context
import com.example.inspiration.data.repository.UserVerificationRepositoryImpl
import com.example.inspiration.data.storage.datastore.StorageDataStore
import com.example.inspiration.data.storage.datastore.UserVerificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class DataModul {

    @Provides
    @Singleton
    fun providesStorageDataStore(context: Context): UserVerificationRepository{
        return StorageDataStore(context = context)
    }

    @Provides
    fun providesUserVerification(
        storageDataStore: StorageDataStore
    ): UserVerificationRepositoryImpl{
        return UserVerificationRepositoryImpl(userVerificationRepository = storageDataStore)
    }
}