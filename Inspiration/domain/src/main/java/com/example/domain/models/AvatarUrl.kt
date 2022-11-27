package com.example.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvatarUrl(
    @Json(name = "small")
    val smallUrl: String,
    @Json(name = "large")
    val largeUrl: String
)
