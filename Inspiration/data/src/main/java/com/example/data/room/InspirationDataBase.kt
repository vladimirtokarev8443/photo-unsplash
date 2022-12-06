package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.PhotoDb

@Database(entities = [PhotoDb::class], version = InspirationDataBase.DB_VERSION)
abstract class InspirationDataBase: RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "inspiration-database"

    }

}