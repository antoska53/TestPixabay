package com.example.testpixabay.imagelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.testpixabay.databinding.ViewHolderImageListBinding
import com.example.testpixabay.network.Image

class ImageListAdapter: Adapter<ImageListViewHolder>() {
    private val _listImage = mutableListOf<Image>()
    val listImage = _listImage

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        return ImageListViewHolder(ViewHolderImageListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = _listImage.size

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.onBind(_listImage[position])
    }

    fun addImages(listImage: List<Image>){
        _listImage.addAll(listImage)
        notifyDataSetChanged()
    }
}