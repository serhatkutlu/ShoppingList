package com.msk.shoppinglist.Repositories

import androidx.lifecycle.LiveData
import com.msk.shoppinglist.Other.Resource
import com.msk.shoppinglist.data.local.ShoppingDAO
import com.msk.shoppinglist.data.local.ShoppingItem
import com.msk.shoppinglist.data.remote.PixabayAPI
import com.msk.shoppinglist.data.remote.responses.ImageResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDAO: ShoppingDAO,
    private val pixabayAPI: PixabayAPI
):ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDAO.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDAO.deleteShoppingItem(shoppingItem)
    }

    override  fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDAO.observeAllShoppingItems()
    }

    override  fun observeTotalPrice(): LiveData<Float> {
       return shoppingDAO.observeTotalPrice()
    }

    override suspend fun searchforImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response=pixabayAPI.searchImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)


            }else{
                Resource.error("An unknown error occured",null)
            }
        }
        catch (e:Exception){
            Resource.error("couldn't reach the server.Check your internet connection",null)
        }
    }
}