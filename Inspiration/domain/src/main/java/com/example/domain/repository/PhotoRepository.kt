package com.example.domain.repository

import com.example.domain.models.DetailsPhoto
import com.example.domain.models.FilterPhoto
import com.example.domain.models.Photo

interface PhotoRepository {

    suspend fun getPhotos(pageIndex: Int, pageSize: Int, filter: FilterPhoto): List<Photo>

    suspend fun getPhotoById(photoId: String): DetailsPhoto

    suspend fun setLike(photoId: String)

    suspend fun deleteLike(photoId: String)

    suspend fun savePhotoToDatabase(photoId: String)

    suspend fun removePhotoFromDatabase(photoId: String)

    suspend fun downloadPhoto(url: String)

}