package com.belkanoid.shoppinglist.presentation.shoppingList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.belkanoid.shoppinglist.data.ShoppingListRepositoryImpl
import com.belkanoid.shoppinglist.domain.model.ShoppingItem
import com.belkanoid.shoppinglist.domain.useCases.*

class MainViewModel : ViewModel() {

    private val repository = ShoppingListRepositoryImpl

    private val getShoppingListUseCase = getShoppingListUseCase(repository)
    private val getShoppingDeleteUseCase = deleteShoppingItemUseCase(repository)
    private val getUpdateShoppingItemUseCase = updateShoppingItemUseCase(repository)

    val shoppingList : LiveData<List<ShoppingItem>> = getShoppingListUseCase.execute()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        getShoppingDeleteUseCase.execute(shoppingItem)
    }

    fun updateShoppingItem(shoppingItem: ShoppingItem) {
        val updatedShoppingItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
        getUpdateShoppingItemUseCase.execute(updatedShoppingItem)
    }

}