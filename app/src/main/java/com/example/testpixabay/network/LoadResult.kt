package com.example.testpixabay.network

sealed class LoadResult
class SuccessResult(val listImages: List<Image>) : LoadResult()
class ErrorResult(val error: Throwable) : LoadResult()
