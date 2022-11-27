package com.example.domain.usecase.photo

import com.example.domain.repository.PhotoRepository
import javax.inject.Inject

class SetLikeUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend fun execute(photoId: String) = repository.setLike(photoId)
}