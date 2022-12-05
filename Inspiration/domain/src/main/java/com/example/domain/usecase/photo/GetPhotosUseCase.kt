package com.example.domain.usecase.photo

import com.example.domain.models.FilterPhoto
import com.example.domain.models.Photo
import com.example.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend fun execute(pageIndex: Int, pageSize: Int, filter: FilterPhoto): List<Photo> {
        return repository.getPhotos(pageIndex, pageSize, filter)
    }
}