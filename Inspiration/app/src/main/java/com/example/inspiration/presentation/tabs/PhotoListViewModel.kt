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
import com.example.domain.enum.Popular
import com.example.domain.models.Photo
import com.example.domain.usecase.photo.GetPhotosUseCase
import com.example.inspiration.models.FilterPhoto
import com.example.inspiration.models.FilterSearchPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {
    private val searchFlow = MutableStateFlow<String>("")
    private val filterFlow = MutableStateFlow(FilterPhoto())
    private val filterSearchFlow = MutableStateFlow(FilterSearchPhoto())

    fun setSeachText(search: String){
        searchFlow.value = search
    }
    fun setfilter(newPopular: Popular){
        filterFlow.update { it.copy(popular = newPopular.value) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhotos(): Flow<PagingData<Photo>> {
        return searchFlow
            .flatMapLatest {
                getPager(it)
            }.cachedIn(viewModelScope)

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhotos2(): Flow<PagingData<Photo>> {
        return filterFlow
            .flatMapLatest {
                getPager(it.popular)
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