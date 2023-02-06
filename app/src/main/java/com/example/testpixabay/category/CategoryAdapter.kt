package com.example.testpixabay.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.testpixabay.data.CategoryImage
import com.example.testpixabay.databinding.ViewHolderCategoryBinding

class CategoryAdapter: Adapter<CategoryViewHolder>() {

    private val _listCategory = CategoryImage.values()
    val listCategory = _listCategory

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ViewHolderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = _listCategory.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(_listCategory[position])
    }
}