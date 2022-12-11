package com.example.inspiration.utils

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.inspiration.R
import com.example.inspiration.presentation.tabs.photo.PhotoListFragmentDirections
import com.google.android.material.card.MaterialCardView

fun Fragment.navigateToDetailsPhoto(photoId: String, cardView: MaterialCardView?){
    val action = PhotoListFragmentDirections.actionPhotoListFragmentToDetailsPhoto(photoId)

    val transitionName = getString(R.string.transition_name_details)
    //val transitionNameAvatar = getString(R.string.image_transition_name_avatar)

    val extras = FragmentNavigatorExtras(
        cardView!! to transitionName
        )

    findNavController().navigate(action, extras)

    //findNavController().navigate(action)
}