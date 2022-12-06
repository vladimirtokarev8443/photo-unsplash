package com.example.inspiration.di.data

import android.app.Application
import androidx.room.Room
import com.example.data.room.InspirationDataBase
import com.example.data.room.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesInspirationDatabase(app: Application): InspirationDataBase {
        return Room.databaseBuilder(
            app,
            InspirationDataBase::class.java,
            InspirationDataBase.DB_NAME
        )
            .build()
    }

    @Provides
    fun providesPhotoDao(db: InspirationDataBase): PhotoDao  {
        return db.photoDao()
    }
}