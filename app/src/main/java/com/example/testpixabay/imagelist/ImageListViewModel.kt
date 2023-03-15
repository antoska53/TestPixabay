package com.example.testpixabay.imagelist

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.testpixabay.network.*

class ImageListViewModel(private val category: String) : ViewModel() {

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = NETWORK_PAGE_SIZE, initialLoadSize = NETWORK_INITIAL_PAGE_SIZE, enablePlaceholders = false)
    ) {
        ImagePagingSource(category)
    }.flow
        .cachedIn(viewModelScope)


    class Factory(val category: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ImageListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ImageListViewModel(category) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
        const val NETWORK_INITIAL_PAGE_SIZE = 100
    }
}