package com.example.testpixabay.imagelist

import androidx.lifecycle.*
import com.example.testpixabay.BuildConfig
import com.example.testpixabay.network.*
import kotlinx.coroutines.launch

class ImageListViewModel(private val category: String) : ViewModel() {

    private val mutableLiveData = MutableLiveData<LoadResult>()
    val liveData: LiveData<LoadResult> = mutableLiveData
    private val _loadState = MutableLiveData<LoadState>()
    val loadState = _loadState
    private val imageApi = RetrofitModule.imageApi

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _loadState.value = Loading()
            mutableLiveData.value =
                try {
                    val listImages = loadImage()
                    SuccessResult(listImages)
                } catch (e: Throwable) {
                    ErrorResult(e)
                }
            _loadState.value = Ending()
        }
    }

    private suspend fun loadImage(): List<Image> {
        return imageApi.getImages(BuildConfig.API_KEY, category).images
    }

    class Factory(val category: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ImageListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ImageListViewModel(category) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}