package com.example.testpixabay.imagelist

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testpixabay.data.CategoryImage
import com.example.testpixabay.databinding.FragmentImageListBinding
import com.example.testpixabay.network.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class FragmentImageList : Fragment() {

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ImageListViewModel by viewModels {
        ImageListViewModel.Factory(
            categoryImage.categoryPixabay
        )
    }
    private val categoryImage by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("category", CategoryImage::class.java)
                ?: CategoryImage.ANIMALS
        } else {
            arguments?.getSerializable("category") as CategoryImage
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCategory.text = categoryImage.categoryRu
        initRecycler()
        binding.errorView.buttonReload.setOnClickListener {
            viewModel.loadData()
        }

        viewModel.liveData.observe(viewLifecycleOwner) { loadResult ->
            when (loadResult) {
                is SuccessResult -> {
                    showResult()
                    updateRecycler(loadResult.listImages)
                }
                is ErrorResult -> showError()
            }
        }

        viewModel.loadState.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                is Loading -> showLoad()
                is Ending -> hideLoad()
            }
        }

    }

    private fun initRecycler() {
        binding.recyclerImage.adapter = ImageListAdapter()
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.recyclerImage.layoutManager = layoutManager
    }

    private fun updateRecycler(listImage: List<Image>) {
        (binding.recyclerImage.adapter as ImageListAdapter).addImages(listImage)
    }

    private fun showError() {
        binding.errorView.root.isVisible = true
        binding.recyclerImage.isVisible = false
    }

    private fun hideError() {
        binding.errorView.root.isVisible = false
    }

    private fun showLoad() {
        hideError()
        binding.progressBar.isVisible = true
        binding.recyclerImage.isVisible = false
    }

    private fun hideLoad() {
        binding.progressBar.isVisible = false
    }

    private fun showResult() {
        binding.recyclerImage.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}