package com.belkanoid.shoppinglist.domain.useCases

import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import javax.inject.Inject

class deleteShoppingItemUseCase @Inject constructor(private val shoppingListRepository: ShoppingListRepository) {

    suspend fun execute(shoppingItem: ShoppingItem) {
        shoppingListRepository.deleteShoppingItem(shoppingItem)
    }
}