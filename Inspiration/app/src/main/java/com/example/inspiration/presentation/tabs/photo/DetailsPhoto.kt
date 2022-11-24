package com.example.inspiration.presentation.tabs.photo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.inspiration.databinding.FragmentDetailsPhotoBinding
import com.example.inspiration.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsPhoto: BaseFragment<FragmentDetailsPhotoBinding>(FragmentDetailsPhotoBinding::inflate) {

    private val viewModel: DetailsPhotoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}