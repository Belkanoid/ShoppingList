package com.belkanoid.shoppinglist.domain.useCases

import com.belkanoid.shoppinglist.domain.model.ShoppingItem
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository

class deleteShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun execute(shoppingItem: ShoppingItem) {
        shoppingListRepository.deleteShoppingItem(shoppingItem)
    }
}