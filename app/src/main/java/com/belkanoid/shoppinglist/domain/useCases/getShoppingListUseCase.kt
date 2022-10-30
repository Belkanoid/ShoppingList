package com.belkanoid.shoppinglist.domain.useCases

import androidx.lifecycle.LiveData
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import javax.inject.Inject

class getShoppingListUseCase @Inject constructor(private val shoppingListRepository: ShoppingListRepository) {

    fun execute() : LiveData<List<ShoppingItem>> {
        return shoppingListRepository.getShoppingList()
    }
}