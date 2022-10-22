package com.belkanoid.shoppinglist.domain.useCases

import com.belkanoid.shoppinglist.domain.model.ShoppingItem
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository

class getShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun execute(shoppingItemId : Int) : ShoppingItem {
        return shoppingListRepository.getShoppingItem(shoppingItemId)
    }
}