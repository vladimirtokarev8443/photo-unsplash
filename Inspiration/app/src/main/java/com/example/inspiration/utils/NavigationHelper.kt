package com.example.inspiration.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inspiration.presentation.tabs.photo.PhotoListFragmentDirections

fun Fragment.navigateToDetailsPhoto(photoId: String){
    val action = PhotoListFragmentDirections.actionPhotoListFragmentToDetailsPhoto(photoId)
    findNavController().navigate(action)
}