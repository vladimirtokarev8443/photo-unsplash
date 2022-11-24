package com.example.inspiration.presentation.tabs.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.api.UnsplashApi
import com.example.data.model.FilterPhoto
import com.example.data.paging.NETWORK_PAGE_SIZE
import com.example.data.paging.PhotoPagingSource
import com.example.domain.enum.Popular
import com.example.domain.models.Photo
import com.example.domain.usecase.photo.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val api: UnsplashApi,
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    private val filterFlow = MutableStateFlow(FilterPhoto())

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhotos2(): Flow<PagingData<Photo>> {
        return filterFlow
            .flatMapLatest {
                getPager(it)
            }.cachedIn(viewModelScope)

    }

    private fun getPager(filter: FilterPhoto): Flow<PagingData<Photo>>{
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            PhotoPagingSource(api, filter)
        }.flow
    }

    fun setfilterPopular(popular: String){
        filterFlow.update { it.copy(popular = popular) }
    }

    fun setFilterPopularSearch(){}

}