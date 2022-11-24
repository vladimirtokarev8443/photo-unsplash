package com.example.inspiration.presentation.tabs.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.DetailsPhoto
import com.example.domain.usecase.photo.GetPhotoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsPhotoViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase
): ViewModel() {
    private val detailsPhotoMutFlow = MutableStateFlow<DetailsPhoto?>(null)
    val detailsPhotoFlow: SharedFlow<DetailsPhoto?> get() = detailsPhotoMutFlow

    fun getDetailsPhoto(photoId: String){
        viewModelScope.launch {
            detailsPhotoMutFlow.value = getPhotoByIdUseCase.execute(photoId)
        }
    }
}