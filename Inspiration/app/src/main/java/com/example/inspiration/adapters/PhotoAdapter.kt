package com.example.inspiration.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Photo
import com.example.inspiration.R
import com.example.inspiration.databinding.ItemPhotoBinding
import com.example.inspiration.models.BlurHashParam
import com.example.inspiration.utils.*
import com.google.android.material.card.MaterialCardView

class PhotoAdapter(
    private val onItemClicked: (String, MaterialCardView?, Boolean?) -> Unit
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
        private val onItemClicked: (String, MaterialCardView?, Boolean?) -> Unit,
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Photo) {
        var isLike = item.isLike
            binding.photoItemPhoto.setImageGlide(item.imageUrl.url, BlurHashParam(item.blurHash, item.width, item.height))
            binding.avatarItemPhoto.setImageGlideCircle(item.author.avatarUrl.smallUrl)
            binding.nameAuthorItemPhoto.text = item.author.name
            binding.favoritesItemPhoto.setImageLike(isLike)

            //binding.avatarItemPhoto.transitionName = itemView.resources.getString(R.string.image_transition_name_avatar, item.id)
            //binding.photoItemPhoto.transitionName = itemView.resources.getString(R.string.image_transition_name, item.id)
            binding.containerItemPhoto.transitionName = itemView.resources.getString(R.string.transition_name, item.id)
            binding.root.setOnClickListener{

                onItemClicked(item.id, binding.containerItemPhoto, null) }

            binding.favoritesItemPhoto.setOnClickListener {
                isLike = isLike.not()
                binding.favoritesItemPhoto.setImageLike(isLike)
                onItemClicked(item.id, null, isLike)
            }
        }
    }

}