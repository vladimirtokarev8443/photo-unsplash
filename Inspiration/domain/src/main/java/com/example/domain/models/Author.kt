package com.example.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    val id: String,
    @Json(name = "username")
    val name: String,
    @Json(name = "profile_image")
    val avatarUrl: ImageUrl
)
