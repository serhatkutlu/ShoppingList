package com.msk.shoppinglist.Ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.msk.shoppinglist.Other.Constants
import com.msk.shoppinglist.Other.Status
import com.msk.shoppinglist.data.local.ShoppingItem
import com.msk.shoppinglist.mainCoroutineRule
import com.msk.shoppinglist.repositories.FakeShoppingRepository
import com.msk.shoppinglist.repositories.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.net.ssl.SSLEngineResult

@ExperimentalCoroutinesApi
class ViewModelTest{
    private lateinit var viewModel: ViewModel
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutine=mainCoroutineRule()
    @Before
    fun setup(){
        viewModel= ViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empt field,returns error`(){
        viewModel.insertShoppingItem("name","","3.0")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with long name,returns error`(){
        val string= buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH+1){
                append(1)
            }
        }
        viewModel.insertShoppingItem(string,"2","3.0")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with long price,returns error`(){
        val price= buildString {
            for (i in 1..Constants.MAX_PRİCE_LENGTH+1){
                append(1)
            }
        }
        viewModel.insertShoppingItem("name","3",price)

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with long amount,returns error`(){

        viewModel.insertShoppingItem("name","9999999999","3.0")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with valid input,returns success`(){

            viewModel.insertShoppingItem("name","3","7")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

    }
}