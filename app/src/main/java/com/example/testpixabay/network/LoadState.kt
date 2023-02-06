package com.example.testpixabay.network

sealed class LoadState
class Loading: LoadState()
class Ending: LoadState()
