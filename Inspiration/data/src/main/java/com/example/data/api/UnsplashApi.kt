package com.example.data.api

import com.example.domain.models.Photo
import com.example.domain.models.SearchPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") nextPageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<Photo>

    @GET("/search/photos")
    suspend fun getSearchPhotos(
        @Query("page") nextPageNumber: Int,
        @Query("per_page") pageSize: Int,
        @Query("query") query: String
    ): SearchPhoto
}