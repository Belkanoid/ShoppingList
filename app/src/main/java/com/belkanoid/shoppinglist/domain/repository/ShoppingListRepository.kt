package com.belkanoid.shoppinglist.domain.repository

import androidx.lifecycle.LiveData
import com.belkanoid.shoppinglist.domain.model.ShoppingItem

interface ShoppingListRepository {

    fun addShoppingItem(shoppingItem: ShoppingItem)

    fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun updateShoppingItem(shoppingItem: ShoppingItem)

    fun getShoppingItem(shoppingItemId: Int) : ShoppingItem

    fun getShoppingList() : LiveData<List<ShoppingItem>>
}