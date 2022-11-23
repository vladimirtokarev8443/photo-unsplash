package com.example.data.repository

import com.example.data.api.UnsplashApi
import com.example.domain.models.Photo
import com.example.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi
): PhotoRepository {

    override suspend fun getPhotos(nextPageNumber: Int, pageSize: Int, popular: String): List<Photo> {
        //return if (query.isBlank()){
         return   api.getPhotos(nextPageNumber, pageSize, popular)
       // } else {
        //    api.getSearchPhotos(nextPageNumber, pageSize, query).result
        //}
    }

}