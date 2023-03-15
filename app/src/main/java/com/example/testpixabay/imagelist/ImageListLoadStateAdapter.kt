package com.example.testpixabay.imagelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.testpixabay.databinding.ViewHolderLoadStateFooterBinding

class ImageListLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ImageListLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: ImageListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ImageListLoadStateViewHolder {
        return ImageListLoadStateViewHolder(
            ViewHolderLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }
}