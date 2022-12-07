package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.room.PhotoDbContract

@Entity(tableName = PhotoDbContract.TABLE_NAME)
data class PhotoDb(
    @PrimaryKey
    @ColumnInfo(name = PhotoDbContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = PhotoDbContract.Columns.IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = PhotoDbContract.Columns.AUTHOR_AVATAR_URL)
    val authorAvatarUrl: String,
    @ColumnInfo(name = PhotoDbContract.Columns.AUTHOR_NAME)
    val authorName: String,
    @ColumnInfo(name = PhotoDbContract.Columns.COUNT_LIKES)
    val countLikes: Int,
    @ColumnInfo(name = PhotoDbContract.Columns.COUNT_DOWNLOAD)
    val countDownload: Int
)
