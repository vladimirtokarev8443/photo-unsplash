package com.example.data.repository

import androidx.paging.*
import com.example.data.api.UnsplashApi
import com.example.data.storage.NETWORK_PAGE_SIZE
import com.example.data.storage.PhotoPagingSource
import com.example.domain.models.Photo
import com.example.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi
): PhotoRepository {

    override suspend fun getPhotos(nextPageNumber: Int, pageSize: Int, query: String): List<Photo> {
        return if (query.isBlank()){
            api.getPhotos(nextPageNumber, pageSize)
        } else {
            api.getSearchPhotos(nextPageNumber, pageSize, query).result
        }
    }

}