package com.msk.shoppinglist.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat

import com.msk.shoppinglist.data.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDAOTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDAO

    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java

        ).allowMainThreadQueries().build()
        dao=database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }
    @Test
    fun insertShoppingItem()= runBlockingTest {

        val shoppingItem=ShoppingItem("banana",1,1f,"fd",1)
        dao.insertShoppingItem(shoppingItem)
        val allshopingItem=dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allshopingItem).contains(shoppingItem)
    }
    @Test
    fun deleteShoppingItem()= runBlockingTest{
        val shoppingItem=ShoppingItem("banana",1,1f,"ddw",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allItem=dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allItem).doesNotContain(shoppingItem)

    }
    @Test
    fun observeTotalPrice()= runBlockingTest {
        val shoppingItem=ShoppingItem("banana",1,1f,"ff",1)
        val shoppingItem1=ShoppingItem("apple",1,1f,"ff",2)
        val shoppingItem2=ShoppingItem("orange",1,1f,"ff",3)

        dao.insertShoppingItem(shoppingItem)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)

        val totalPrice=dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPrice).isEqualTo(3f)
    }
}