package com.msk.shoppinglist.Ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.shoppinglist.Other.Event
import com.msk.shoppinglist.Other.Resource
import com.msk.shoppinglist.Repositories.ShoppingRepository
import com.msk.shoppinglist.data.local.ShoppingItem
import com.msk.shoppinglist.data.remote.responses.ImageResponse
import kotlinx.coroutines.launch

class ViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository
): ViewModel() {
    val shoppingItem=repository.observeAllShoppingItem()

    val totalPrice=repository.observeTotalPrice()

    private val _images=MutableLiveData<Event<Resource<ImageResponse>>>()
    val images:LiveData<Event<Resource<ImageResponse>>> =_images

    private val _currimages=MutableLiveData<String>()
    val currimages:LiveData<String> =_currimages


    private val _insertShoppingİtemStatus=MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingİtemStatus:LiveData<Event<Resource<ShoppingItem>>> =_insertShoppingİtemStatus

    fun SetCurrImageUrl(url:String){
        _currimages.postValue(url)
    }
    fun deleteShoppingItem(shoppingItem: ShoppingItem)=viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }
    fun insertShoppingItemInToDB(shoppingItem: ShoppingItem)=viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }
    fun insertShoppingItem(name:String,amount:String,price:String){

    }
    fun searchForImage(imageQuery:String){

    }

}