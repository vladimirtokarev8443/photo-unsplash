package com.example.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    val id: String,
    @Json(name = "urls")
    val imageUrl: ImageUrl,
    @Json(name = "user")
    val author: Author,
    @Json(name = "liked_by_user")
    val isLike: Boolean,
    @Json(name = "likes")
    val countLikes: Int
)
