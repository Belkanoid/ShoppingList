package com.belkanoid.shoppinglist.domain.useCases

import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import javax.inject.Inject

class getShoppingItemUseCase @Inject constructor(private val shoppingListRepository: ShoppingListRepository) {

    suspend fun execute(shoppingItemId : Int) : ShoppingItem {
        return shoppingListRepository.getShoppingItem(shoppingItemId)
    }
}