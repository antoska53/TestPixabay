package com.example.testpixabay.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("id")
    val id: Long,

    @SerialName("previewURL")
    val previewURL: String,

    @SerialName("previewWidth")
    val previewWidth: Long,

    @SerialName("previewHeight")
    val previewHeight: Long,

    @SerialName("largeImageURL")
    val largeImageURL: String,

    @SerialName("imageWidth")
    val imageWidth: Long,

    @SerialName("imageHeight")
    val imageHeight: Long
)