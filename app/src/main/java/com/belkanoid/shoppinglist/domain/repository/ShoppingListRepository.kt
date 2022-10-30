package com.belkanoid.shoppinglist.domain.repository

import androidx.lifecycle.LiveData
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem

interface ShoppingListRepository {

    suspend fun addShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    suspend fun updateShoppingItem(shoppingItem: ShoppingItem)

    suspend fun getShoppingItem(shoppingItemId: Int) : ShoppingItem

    fun getShoppingList() : LiveData<List<ShoppingItem>>
}