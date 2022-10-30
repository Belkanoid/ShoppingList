package com.belkanoid.shoppinglist.di

import android.app.Application
import androidx.room.Room
import com.belkanoid.shoppinglist.data.database.ShoppingListDatabase
import com.belkanoid.shoppinglist.data.database.dao.ShoppingListDao
import com.belkanoid.shoppinglist.data.repositoryImpl.ShoppingListRepositoryImpl
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideShoppingDao(application: Application) : ShoppingListDao {
        return ShoppingListDatabase.getInstance(application).shoppingListDao()
    }


}