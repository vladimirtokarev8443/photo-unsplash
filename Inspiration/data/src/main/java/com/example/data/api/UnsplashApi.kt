package com.example.data.api

import com.example.domain.models.DetailsPhoto
import com.example.domain.models.Photo
import com.example.domain.models.SearchPhoto
import retrofit2.http.*

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
        @Query("query") query: String
        //@Query("order_by") popular: String,
        //@Query("orientation") orientation: String
    ): SearchPhoto

    @GET("/photos/{id}")
    suspend fun getPhotoById(
    @Path("id") photoId: String
    ): DetailsPhoto

    @POST("/photos/{id}/like")
    suspend fun setLike(
        @Path("id") photoId: String
    )

    @DELETE("/photos/{id}/like")
    suspend fun deleteLike(
        @Path("id") photoId: String
    )
}