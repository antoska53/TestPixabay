package com.example.testpixabay.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("api")
    suspend fun getImages(@Query("key") apiKey: String, @Query("category") category: String): ImageResponse
}