package com.belkanoid.shoppinglist.domain.useCases

import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import javax.inject.Inject

class addShoppingItemUseCase @Inject constructor(private val shoppingListRepository: ShoppingListRepository) {
    suspend fun execute(shoppingItem: ShoppingItem) {
        shoppingListRepository.addShoppingItem(shoppingItem)
    }
}