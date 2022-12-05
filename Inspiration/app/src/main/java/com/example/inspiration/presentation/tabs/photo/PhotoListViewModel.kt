package com.example.inspiration.presentation.tabs.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.domain.models.FilterPhoto
import com.example.data.paging.LoaderPhoto
import com.example.data.paging.PAGE_SIZE
import com.example.data.paging.PhotoPagingSource
import com.example.domain.models.Photo
import com.example.domain.usecase.photo.DeleteLikeUseCase
import com.example.domain.usecase.photo.GetPhotosUseCase
import com.example.domain.usecase.photo.SetLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val setLikeUseCase: SetLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase
) : ViewModel() {

    private var job: Job? = null

    private val localChangeLike = MutableStateFlow(mutableMapOf<String, Boolean>())

    private val filterFlow = MutableStateFlow(FilterPhoto())

    val pagingDataFlow: Flow<PagingData<Photo>>

    init {
        pagingDataFlow = combine(
            getPhotos(),
            localChangeLike,
            this::mergeFlow
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhotos(): Flow<PagingData<Photo>> {
        return filterFlow
            .flatMapLatest {
                getPager(it)
            }.cachedIn(viewModelScope)

    }

    private fun getPager(filter: FilterPhoto): Flow<PagingData<Photo>> {
        val loader: LoaderPhoto = {pageIndex, pageSize ->
            getPhotosUseCase.execute(pageIndex, pageSize, filter)
        }

        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            PhotoPagingSource(loader)
        }.flow
    }


    private fun mergeFlow(
        photo: PagingData<Photo>,
        localChange: MutableMap<String, Boolean>
    ): PagingData<Photo> {
        return photo.map { photo ->
            val isLike = localChange[photo.id]
            isLike?.let { photo.copy(isLike = it) } ?: photo
        }
    }

    fun setLocalChange(photoId: String, isLike: Boolean){
        localChangeLike.update {
            it[photoId] = isLike
            it
        }
    }

    fun setfilterPopular(popular: String) {
        filterFlow.update { it.copy(popular = popular) }
    }

    fun setFilterQuerySearch(query: String) {
        filterFlow.update { it.copy(querySearch = query) }
    }

    fun updateLike(isLike: Boolean, photoId: String) {
        job?.cancel()

        try {
            job = viewModelScope.launch {
                if (isLike) setLikeUseCase.execute(photoId) else deleteLikeUseCase.execute(photoId)
                setLocalChange(photoId, isLike)
            }
        } catch (e: Exception){}
    }

}