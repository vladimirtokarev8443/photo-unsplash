package com.example.inspiration.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.inspiration.R
import com.example.inspiration.models.BlurHashParam
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T
): T {

    return bindingInflater.invoke(LayoutInflater.from(context), this, false)
}

fun Int.fromDpToPixels(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return this * pixelInDp
}


fun ImageView.setImageGlide(url: String, blur: BlurHashParam) {
    Glide.with(this)
        .load(url)
        .placeholder(
            BitmapDrawable(
                resources,
                BlurHashDecoder.decode(
                    blur.blurHash,
                    blur.width,
                    blur.height
                )
            )
        )
        .into(this)
}

fun ImageView.setImageGlideCircle(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}

fun ImageButton.setImageLike(isLike: Boolean) {
    setImageResource(if (isLike) R.drawable.ic_like_selected else R.drawable.ic_like)
}

//устанавливает иконку но больше не нужен
fun View.setIconSearchView() {
    findViewById<SearchView>(R.id.actionSearch)
        .findViewById<ImageView>(R.id.search_button)
        .setImageResource(R.drawable.ic_search)
}

fun SearchView.changeText(): Flow<String> {
    return callbackFlow<String> {
        val textListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let(::trySendBlocking)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        }
        this@changeText.setOnQueryTextListener(textListener)

        awaitClose {
            this@changeText.setOnQueryTextListener(null)
        }
    }
}

fun MenuItem.expandedSearch(isExpand: () -> Unit, isCollapse: () -> Unit) {
    setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            isExpand()
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            isCollapse()
            return true
        }
    })
}



