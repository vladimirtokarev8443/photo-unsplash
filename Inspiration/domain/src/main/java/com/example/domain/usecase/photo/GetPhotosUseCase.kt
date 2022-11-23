package com.example.domain.usecase.photo

import com.example.domain.models.Photo
import com.example.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    suspend fun execute(nextPageNumber: Int, pageSize: Int, popular: String): List<Photo> = photoRepository.getPhotos(nextPageNumber, pageSize, popular)

}