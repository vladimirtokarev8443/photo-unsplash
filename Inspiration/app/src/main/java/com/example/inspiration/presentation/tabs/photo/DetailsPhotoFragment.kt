package com.example.inspiration.presentation.tabs.photo

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.domain.models.DetailsPhoto
import com.example.inspiration.R
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

        binding.toolbarDetailsPhoto.setupWithNavController(findNavController())

        getDetailsPhoto()

        observeViewModel()

        listeners()
    }

    private fun getDetailsPhoto(){
        viewModel.getDetailsPhoto(args.photoId)
    }

    private fun observeViewModel(){
        viewModel.detailsPhotoFlow
            .onEach { detailsPhoto -> detailsPhoto?.let(::bindDetailsPhoto) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun bindDetailsPhoto(photo: DetailsPhoto) = with(binding){
        photoDetailsPhoto.setImageGlide(photo.imageUrl.url)
        avatarDetailsPhoto.setImageGlideCircle(photo.author.avatarUrl.largeUrl)

        nameAuthorDetailsPhoto.text = photo.author.name
        discriptionsDetailsPhoto.text = photo.discription
        countLikesDetailsPhoto.text = photo.countLikes.toString()
        countDownloadDetailsPhoto.text = photo.countDownload.toString()

        likeDetailsPhoto.setImageResource(if (photo.isLike) R.drawable.ic_like_selected else R.drawable.ic_like)
    }

    private fun listeners(){
        binding.likeDetailsPhoto.setOnClickListener {
            viewModel.onClicLike(args.photoId, ::transmittingResultFragment)
        }

        binding.downloadDetailsPhoto.setOnClickListener {

        }
    }

    private fun transmittingResultFragment(isLike: Boolean){
        parentFragmentManager.setFragmentResult(REQUEST_CODE, bundleOf(LIKE_KEY to isLike, PHOTO_ID_KEY to args.photoId))
    }

    companion object{
        const val REQUEST_CODE = "request_code_is_like"
        const val LIKE_KEY = "like_key"
        const val PHOTO_ID_KEY = "photo_id_key"


    }


}