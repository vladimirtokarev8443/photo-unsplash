package com.example.inspiration.di.domain

import com.example.domain.repository.PhotoRepository
import com.example.domain.repository.VerificationRepository
import com.example.domain.usecase.verification.GetAccessTokenUseCase
import com.example.domain.usecase.verification.GetVerificationStatusUseCase
import com.example.domain.usecase.verification.SaveAccessTokenUseCase
import com.example.domain.usecase.verification.SaveVerificationStatusUseCase
import com.example.domain.usecase.photo.GetPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModul {

    @Provides
    fun providesSaveAccessTokenUseCase(repository: VerificationRepository): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(repository)
    }
    @Provides
    fun providesGetAccessTokenUseCase(repository: VerificationRepository): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(repository)
    }
    @Provides
    fun providesSaveVerificationStatus(repository: VerificationRepository): SaveVerificationStatusUseCase {
        return SaveVerificationStatusUseCase(repository)
    }
    @Provides
    fun providesGetVerificationStatus(repository: VerificationRepository): GetVerificationStatusUseCase {
        return GetVerificationStatusUseCase(repository)
    }
    @Provides
    fun providesGetPhotosUseCase(repository: PhotoRepository): GetPhotosUseCase {
        return GetPhotosUseCase(repository)
    }
}