package com.example.testpixabay.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("total")
    val total: Long,

    @SerialName("totalHits")
    val totalHits: Long,

    @SerialName("hits")
    val images: List<Image>
)


