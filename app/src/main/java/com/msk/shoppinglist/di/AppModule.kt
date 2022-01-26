package com.msk.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.msk.shoppinglist.Other.Constants.BASE_URL
import com.msk.shoppinglist.Other.Constants.DATABASE_NAME
import com.msk.shoppinglist.Repositories.DefaultShoppingRepository
import com.msk.shoppinglist.Repositories.ShoppingRepository
import com.msk.shoppinglist.data.local.ShoppingDAO
import com.msk.shoppinglist.data.local.ShoppingItem
import com.msk.shoppinglist.data.local.ShoppingItemDatabase
import com.msk.shoppinglist.data.remote.PixabayAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao:ShoppingDAO,
        api: PixabayAPI
    )=DefaultShoppingRepository(dao,api) as ShoppingRepository



    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context:Context
    )= Room.databaseBuilder(
        context,
        ShoppingItemDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    )=database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayAPI():PixabayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}