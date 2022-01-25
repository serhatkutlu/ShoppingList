package com.msk.shoppinglist.data.remote.responses

data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)