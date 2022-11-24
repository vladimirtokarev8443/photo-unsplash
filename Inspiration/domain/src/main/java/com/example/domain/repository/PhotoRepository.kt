package com.example.domain.repository

import com.example.domain.models.DetailsPhoto

interface PhotoRepository {

    suspend fun getPhotoById(photoId: String): DetailsPhoto

    suspend fun setLikePhoto()

}