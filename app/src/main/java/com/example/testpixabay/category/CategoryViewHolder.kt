package com.example.testpixabay.category

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testpixabay.R
import com.example.testpixabay.data.CategoryImage
import com.example.testpixabay.databinding.ViewHolderCategoryBinding

class CategoryViewHolder(private val binding: ViewHolderCategoryBinding) :
    ViewHolder(binding.root) {

    fun onBind(category: CategoryImage) {
        binding.butViewCategory.text = category.categoryRu
        binding.butViewCategory.setOnClickListener {
            val bundle = bundleOf("category" to category)
            binding.root.findNavController()
                .navigate(R.id.action_fragmentCategory_to_fragmentImageList, bundle)
        }
    }
}