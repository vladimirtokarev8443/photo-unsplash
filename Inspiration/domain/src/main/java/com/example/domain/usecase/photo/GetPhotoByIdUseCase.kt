package com.example.domain.usecase.photo

import com.example.domain.models.DetailsPhoto
import com.example.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPhotoByIdUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend fun execute(photoId: String): DetailsPhoto = withContext(Dispatchers.IO){
        return@withContext repository.getPhotoById(photoId)
    }
}