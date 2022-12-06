package com.example.data.util

import com.example.data.model.PhotoDb
import com.example.domain.models.DetailsPhoto

fun DetailsPhoto.toPhotoDb(): PhotoDb{
    return PhotoDb(
        id = id,
        imageUrl = imageUrl.url,
        authorAvatarUrl = author.avatarUrl.largeUrl,
        authorName = author.name,
        countLikes = countLikes,
        countDownload = countDownload
    )
}