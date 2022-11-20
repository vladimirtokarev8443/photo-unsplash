package com.example.inspiration.presentation.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.storage.NETWORK_PAGE_SIZE
import com.example.data.storage.PhotoPagingSource
import com.example.domain.models.Photo
import com.example.domain.usecase.photo.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    val photoList: Flow<PagingData<Photo>> = getPhotos()
    val photoSearch: Flow<PagingData<Photo>> = flowOf(PagingData.empty())


    private fun getPhotos(): Flow<PagingData<Photo>>{
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            PhotoPagingSource(getPhotosUseCase)
        }.flow.cachedIn(viewModelScope)
    }

    fun searchPhoto(query: String): Flow<PagingData<Photo>>{


        return Pager(
                PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = false
                )
            ) {
                PhotoPagingSource(getPhotosUseCase, query)
            }.flow


    }

}