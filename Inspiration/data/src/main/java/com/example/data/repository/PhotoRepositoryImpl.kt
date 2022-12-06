package com.example.data.repository

import androidx.room.Transaction
import com.example.data.api.UnsplashApi
import com.example.data.room.PhotoDao
import com.example.data.util.toPhotoDb
import com.example.domain.models.DetailsPhoto
import com.example.domain.models.FilterPhoto
import com.example.domain.models.Photo
import com.example.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi,
    private val photoDao: PhotoDao
): PhotoRepository {
    override suspend fun getPhotos(pageIndex: Int, pageSize: Int, filter: FilterPhoto): List<Photo> {
        return if (filter.querySearch.isBlank()) {
                api.getPhotos(
                    nextPageNumber = pageIndex,
                    pageSize = pageSize,
                    popular = filter.popular
                )
            } else {
                api.getSearchPhotos(
                    nextPageNumber = pageIndex,
                    pageSize = pageSize,
                    query = filter.querySearch
                    //popular = filter.popularSearch,
                    //orientation = filter.orientation
                ).result
}
    }

    override suspend fun getPhotoById(photoId: String): DetailsPhoto = api.getPhotoById(photoId = photoId)

    @Transaction
    override suspend fun setLike(photoId: String) {
        api.setLike(photoId)
        savePhotoToDatabase(photoId)
    }

    override suspend fun savePhotoToDatabase(photoId: String) {
        val photoDb = getPhotoById(photoId).toPhotoDb()
        photoDao.insertPhoto(photoDb)
    }

    @Transaction
    override suspend fun deleteLike(photoId: String) {
        api.deleteLike(photoId)
        removePhotoFromDatabase(photoId)
    }

    override suspend fun removePhotoFromDatabase(photoId: String) {
        photoDao.removePhoto(photoId)
    }

    override suspend fun downloadPhoto(url: String) {

    }

}