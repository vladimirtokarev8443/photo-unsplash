package com.example.inspiration.presentation.tabs.photo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.domain.models.DetailsPhoto
import com.example.inspiration.databinding.FragmentDetailsPhotoBinding
import com.example.inspiration.utils.BaseFragment
import com.example.inspiration.utils.setImageGlide
import com.example.inspiration.utils.setImageGlideCircle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsPhotoFragment: BaseFragment<FragmentDetailsPhotoBinding>(FragmentDetailsPhotoBinding::inflate) {

    private val viewModel: DetailsPhotoViewModel by viewModels()
    private val args: DetailsPhotoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetailsPhoto()
        observeViewModel()
    }

    private fun getDetailsPhoto(){
        viewModel.getDetailsPhoto(args.photoId)
    }

    private fun observeViewModel(){
        viewModel.detailsPhotoFlow
            .onEach { detailsPhoto ->
                detailsPhoto?.let(::bindDetailsPhoto)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun bindDetailsPhoto(photo: DetailsPhoto){
        binding.photoDetailsPhoto.setImageGlide(photo.imageUrl.url)
        binding.avatarDetailsPhoto.setImageGlideCircle(photo.author.avatarUrl.url)

    }

}