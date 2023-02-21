package com.example.testpixabay.imagelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.testpixabay.databinding.ViewHolderImageListBinding
import com.example.testpixabay.network.Image

class ImageListPagingAdapter(diffCallback: DiffUtil.ItemCallback<Image>) :
    PagingDataAdapter<Image, ImageListViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        return ImageListViewHolder(
            ViewHolderImageListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    object ImageComparator : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}