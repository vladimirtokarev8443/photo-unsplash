package com.example.data.room

import com.example.domain.models.Author
import com.example.domain.models.ImageUrl

object PhotoDbContract {
    const val TABLE_NAME = "Photos"

    object Columns {
        const val ID = "id"
        const val IMAGE_URL = "image_url"
        const val AUTHOR_AVATAR_URL = "author_avatar_url"
        const val AUTHOR_NAME = "author_name"
        const val AUTHOR_NICKNAME = "author_nickname"
        const val COUNT_LIKES = "count_like"
        const val COUNT_DOWNLOAD = "count_download"
    }
}
