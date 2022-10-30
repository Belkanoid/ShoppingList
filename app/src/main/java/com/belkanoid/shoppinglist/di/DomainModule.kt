package com.belkanoid.shoppinglist.di

import com.belkanoid.shoppinglist.data.repositoryImpl.ShoppingListRepositoryImpl
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Singleton
    @Binds
    fun bindsShoppingListRepository(impl: ShoppingListRepositoryImpl) : ShoppingListRepository
}