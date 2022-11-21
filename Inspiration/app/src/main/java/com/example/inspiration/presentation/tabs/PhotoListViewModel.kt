package com.example.inspiration.presentation.tabs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.paging.NETWORK_PAGE_SIZE
import com.example.data.paging.PhotoPagingSource
import com.example.domain.models.Photo
import com.example.domain.usecase.photo.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {
    private val searchFlow = MutableStateFlow<String>("")

    fun setSeachText(search: String){
        searchFlow.value = search
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhotos(): Flow<PagingData<Photo>> {
        return searchFlow
            .flatMapLatest {
                getPager(it)
            }.cachedIn(viewModelScope)

    }

    private fun getPager(text: String): Flow<PagingData<Photo>>{
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            PhotoPagingSource(getPhotosUseCase, text)
        }.flow
    }

}