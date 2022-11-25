package com.example.domain.repository

import com.example.domain.models.DetailsPhoto

interface PhotoRepository {

    suspend fun getPhotoById(photoId: String): DetailsPhoto

    suspend fun setLike(photoId: String)

    suspend fun deleteLike(photoId: String)

    suspend fun savePhotoWithLike()

    suspend fun deletePhotoWithLikeById()

    suspend fun downloadPhoto(url: String)

}