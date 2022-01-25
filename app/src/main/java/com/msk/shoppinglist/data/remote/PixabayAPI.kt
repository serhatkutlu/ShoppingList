package com.msk.shoppinglist.data.remote

import com.msk.shoppinglist.BuildConfig
import com.msk.shoppinglist.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {
    @GET("/api/")
    suspend fun searchImage(
        @Query("q") searchQuery:String,
        @Query("key") api_key:String =BuildConfig.API_KEY
    ):Response<ImageResponse>
}