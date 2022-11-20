package com.example.inspiration.utils

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class ItemPhotoOffset (private val context: Context): RecyclerView.ItemDecoration() {
    private val offset = MARGIN.fromDpToPixels(context)
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        with(outRect){
            top = offset
            bottom = offset
            left = offset
            right = offset
        }
    }

    companion object{
        private const val MARGIN = 8
    }
}