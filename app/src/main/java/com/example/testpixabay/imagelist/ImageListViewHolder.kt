package com.example.testpixabay.imagelist

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.testpixabay.R
import com.example.testpixabay.databinding.ViewHolderImageListBinding
import com.example.testpixabay.network.Image

class ImageListViewHolder(private val binding: ViewHolderImageListBinding) :
    ViewHolder(binding.root) {

    fun onBind(image: Image) {
        binding.ivImageList.setOnClickListener {
            val bundle = bundleOf("image" to image.largeImageURL)
            binding.root.findNavController()
                .navigate(R.id.action_fragmentImageList_to_fragmentWallpaper, bundle)
        }

        Glide.with(binding.root)
            .load(image.previewURL)
            .into(binding.ivImageList)
    }
}