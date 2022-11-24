package com.example.data.api

import com.example.domain.models.DetailsPhoto
import com.example.domain.models.Photo
import com.example.domain.models.SearchPhoto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") nextPageNumber: Int,
        @Query("per_page") pageSize: Int,
        @Query("order_by") popular: String
    ): List<Photo>

    @GET("/search/photos")
    suspend fun getSearchPhotos(
        @Query("page") nextPageNumber: Int,
        @Query("per_page") pageSize: Int,
        @Query("query") query: String,
        @Query("popular") popular: String,
        @Query("orientation") orientation: String
    ): SearchPhoto

    @GET("/search/{id}")
    suspend fun getPhotoById(
    @Path("id") id: String
    ): DetailsPhoto
}