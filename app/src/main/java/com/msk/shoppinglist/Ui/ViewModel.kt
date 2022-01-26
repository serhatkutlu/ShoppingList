package com.msk.shoppinglist.Ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.shoppinglist.Other.Constants
import com.msk.shoppinglist.Other.Event
import com.msk.shoppinglist.Other.Resource
import com.msk.shoppinglist.Repositories.ShoppingRepository
import com.msk.shoppinglist.data.local.ShoppingItem
import com.msk.shoppinglist.data.remote.responses.ImageResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository
): ViewModel() {
    val shoppingItem=repository.observeAllShoppingItem()

    val totalPrice=repository.observeTotalPrice()

    private val _images=MutableLiveData<Event<Resource<ImageResponse>>>()
    val images:LiveData<Event<Resource<ImageResponse>>> =_images

    private val _currimages=MutableLiveData<String>()
    val currimages:LiveData<String> =_currimages


    private val _insertShoppingItemStatus=MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus:LiveData<Event<Resource<ShoppingItem>>> =_insertShoppingItemStatus

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
        if (name.isEmpty()||amount.isEmpty()||price.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The fields must not be empty",null)))
            return
        }
        if (name.length>Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the item "+
                "must not execed ${Constants.MAX_NAME_LENGTH} characters",null)))
            return
        }
        if (price.length>Constants.MAX_PRİCE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price of the item "+
                    "must not execed ${Constants.MAX_PRİCE_LENGTH} characters",null)))
            return
        }
        val amount=try {
            amount.toInt()
        }catch (e:Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("please enter a valid amount ",null)))
        return
        }
        val shoppingItem=ShoppingItem(name,amount, price.toFloat(),_currimages.value ?: "")
        insertShoppingItemInToDB(shoppingItem)
        SetCurrImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }
    fun searchForImage(imageQuery:String){
    if (imageQuery.isEmpty()){
        return
    }
        _images.value= Event(Resource.loading(null))
        viewModelScope.launch {
            val response =repository.searchforImage(imageQuery)
            _images.value=Event(response)
        }
    }

}