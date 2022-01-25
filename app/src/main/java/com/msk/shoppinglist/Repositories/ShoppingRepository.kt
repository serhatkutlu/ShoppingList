package com.msk.shoppinglist.Repositories

import androidx.lifecycle.LiveData
import com.msk.shoppinglist.Other.Resource
import com.msk.shoppinglist.data.local.ShoppingItem
import com.msk.shoppinglist.data.remote.responses.ImageResponse
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

     fun observeAllShoppingItem():LiveData<List<ShoppingItem>>

     fun observeTotalPrice():LiveData<Float>

    suspend fun searchforImage(imageQuery:String):Resource<ImageResponse>



}