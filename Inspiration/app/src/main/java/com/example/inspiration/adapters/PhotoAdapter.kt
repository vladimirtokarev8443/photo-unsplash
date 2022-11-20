package com.example.inspiration.adapters

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Photo
import com.example.inspiration.databinding.ItemPhotoBinding
import com.example.inspiration.utils.setImageGlide
import com.example.inspiration.utils.setImageGlideCircle
import com.example.inspiration.utils.viewBinding

class PhotoAdapter(
    private val onItemClicked: (Int) -> Unit
) : PagingDataAdapter<Photo, PhotoAdapter.PhotoHolder>(PhotoDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        return PhotoHolder(parent.viewBinding(ItemPhotoBinding::inflate), onItemClicked)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class PhotoDiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    class PhotoHolder(
        private val binding: ItemPhotoBinding,
        private val onItemClicked: (Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Photo){
            binding.photoItemPhoto.setImageGlide(item.imageUrl.url)
            binding.avatarItemPhoto.setImageGlideCircle(item.author.avatarUrl.url)
            binding.nameAuthorItemPhoto.text = item.author.name
        }
    }

}