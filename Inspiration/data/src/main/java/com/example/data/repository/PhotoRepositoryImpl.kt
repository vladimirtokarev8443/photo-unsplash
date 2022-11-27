package com.example.data.repository

import com.example.data.api.UnsplashApi
import com.example.domain.models.DetailsPhoto
import com.example.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi
): PhotoRepository {
    override suspend fun getPhotoById(photoId: String): DetailsPhoto = api.getPhotoById(photoId = photoId)

    override suspend fun setLike(photoId: String) = api.setLike(photoId = photoId)

    override suspend fun deleteLike(photoId: String) = api.deleteLike(photoId = photoId)

    override suspend fun savePhotoWithLike() {

    }

    override suspend fun deletePhotoWithLikeById() {

    }

    override suspend fun downloadPhoto(url: String) {

    }

}