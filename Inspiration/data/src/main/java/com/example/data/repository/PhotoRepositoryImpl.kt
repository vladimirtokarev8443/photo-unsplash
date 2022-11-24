package com.example.data.repository

import com.example.data.api.UnsplashApi
import com.example.domain.models.DetailsPhoto
import com.example.domain.models.Photo
import com.example.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi
): PhotoRepository {
    override suspend fun getPhotoById(photoId: String): DetailsPhoto = api.getPhotoById(id = photoId)

    override suspend fun setLikePhoto() {

    }


}