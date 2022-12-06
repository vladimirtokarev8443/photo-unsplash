package com.example.inspiration.presentation.tabs.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.DetailsPhoto
import com.example.domain.usecase.photo.DeleteLikeUseCase
import com.example.domain.usecase.photo.GetPhotoByIdUseCase
import com.example.domain.usecase.photo.SetLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsPhotoViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    private val setLikeUseCase: SetLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase
): ViewModel() {
    private var job: Job? = null

    private val detailsPhotoMutFlow = MutableStateFlow<DetailsPhoto?>(null)
    val detailsPhotoFlow: StateFlow<DetailsPhoto?> get() = detailsPhotoMutFlow

    fun getDetailsPhoto(photoId: String){
        try {
            viewModelScope.launch {
                detailsPhotoMutFlow.value = getPhotoByIdUseCase.execute(photoId)
            }
        } catch (e: Exception){}
    }

    fun onClicLike(photoId: String){
        try {
            val isLike = detailsPhotoMutFlow.value?.isLike?.not() ?: return

            detailsPhotoMutFlow.update { it?.copy(isLike = isLike)  }

            job?.cancel()

            job = viewModelScope.launch {
                if (isLike){
                    setLikeUseCase.execute(photoId)
                } else {
                    deleteLikeUseCase.execute(photoId)
                }
            }
        } catch (e: Exception){}

    }

}