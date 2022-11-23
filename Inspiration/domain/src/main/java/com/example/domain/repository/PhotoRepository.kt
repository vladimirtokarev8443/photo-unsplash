package com.example.domain.repository

import com.example.domain.models.Photo

interface PhotoRepository {

    suspend fun getPhotos(nextPageNumber: Int, pageSize: Int, popular: String): List<Photo>

}