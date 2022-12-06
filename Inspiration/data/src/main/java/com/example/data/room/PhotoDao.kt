package com.example.data.room

import androidx.room.*
import com.example.data.model.PhotoDb
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM ${PhotoDbContract.TABLE_NAME}")
    suspend fun getAllPhotos(): Flow<List<PhotoDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photoDb: PhotoDb)

    @Query("DELETE FROM ${PhotoDbContract.TABLE_NAME} WHERE ${PhotoDbContract.Columns.ID}=:photoId")
    suspend fun removePhoto(photoId: String)

    @Query("DELETE FROM ${PhotoDbContract.TABLE_NAME}")
    suspend fun removeAllPhotos()

}