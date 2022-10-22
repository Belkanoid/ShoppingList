package com.belkanoid.shoppinglist.domain.useCases

import androidx.lifecycle.LiveData
import com.belkanoid.shoppinglist.domain.model.ShoppingItem
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository

class getShoppingListUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun execute() : LiveData<List<ShoppingItem>> {
        return shoppingListRepository.getShoppingList()
    }
}