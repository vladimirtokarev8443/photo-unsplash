package com.example.inspiration.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.inspiration.R

inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T): T{

    return bindingInflater.invoke(LayoutInflater.from(context), this, false)
}

fun Int.fromDpToPixels(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return this * pixelInDp
}


fun ImageView.setImageGlide(url: String){
    Glide.with(this)
        .load(url)
        .into(this)
}
fun ImageView.setImageGlideCircle(url: String){
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}

fun View.setIconSearchView(){
    findViewById<SearchView>(R.id.actionSearch)
        .findViewById<ImageView>(R.id.search_button)
        .setImageResource(R.drawable.ic_search)
}