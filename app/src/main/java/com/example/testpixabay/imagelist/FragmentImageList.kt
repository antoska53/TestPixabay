package com.example.testpixabay.imagelist

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.testpixabay.data.CategoryImage
import com.example.testpixabay.databinding.FragmentImageListBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
        val pagingAdapter = ImageListPagingAdapter(ImageListPagingAdapter.ImageComparator)
        binding.recyclerImage.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = ImageListLoadStateAdapter{ pagingAdapter.retry() },
            footer = ImageListLoadStateAdapter{ pagingAdapter.retry() }
        )

        initRecycler()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingAdapter.loadStateFlow.collect {loadStates ->
                    binding.progressBar.isVisible =
                        loadStates.refresh is LoadState.Loading
                    binding.recyclerImage.isVisible =
                        loadStates.refresh is LoadState.NotLoading
                    binding.errorView.root.isVisible =
                        loadStates.refresh is LoadState.Error
                }
            }
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        binding.errorView.buttonReload.setOnClickListener {
            pagingAdapter.retry()
        }
    }

    private fun initRecycler() {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.recyclerImage.layoutManager = layoutManager
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}