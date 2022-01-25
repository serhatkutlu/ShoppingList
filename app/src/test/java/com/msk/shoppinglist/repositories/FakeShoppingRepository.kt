package com.msk.shoppinglist.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msk.shoppinglist.Other.Resource
import com.msk.shoppinglist.Repositories.ShoppingRepository
import com.msk.shoppinglist.data.local.ShoppingItem
import com.msk.shoppinglist.data.remote.responses.ImageResponse

class FakeShoppingRepository:ShoppingRepository {
    private val shoppingItems= mutableListOf<ShoppingItem>()
    private val  observableShoppingItems=MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice=MutableLiveData<Float>()

    private var shouldReturnNetworkError=false
    fun setshouldReturnNetworkError (value:Boolean){
        shouldReturnNetworkError=value
    }
    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }
    private fun getTotalPrice():Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override  fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override  fun observeTotalPrice(): LiveData<Float> {
        return observeTotalPrice()
    }

    override suspend fun searchforImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("error",null)

        }else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}