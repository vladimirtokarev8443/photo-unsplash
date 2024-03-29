package com.example.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailsPhoto(
    val id: String,
    @Json(name = "urls")
    val imageUrl: ImageUrl,
    @Json(name = "blur_hash")
    val blurHash: String,
    val width: Int,
    val height: Int,
    @Json(name = "color")
    val color: String,
    @Json(name = "user")
    val author: Author,
    @Json(name = "liked_by_user")
    val isLike: Boolean,
    @Json(name = "likes")
    val countLikes: Int,
    @Json(name = "downloads")
    val countDownload: Int,
    @Json(name = "description")
    val discription: String?
)
