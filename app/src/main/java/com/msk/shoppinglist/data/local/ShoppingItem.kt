package com.msk.shoppinglist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="shopping_item" )
data class ShoppingItem(
    var name:String,
    var amount:Int,
    var price:Float,
    var imageURL:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null
) {
}