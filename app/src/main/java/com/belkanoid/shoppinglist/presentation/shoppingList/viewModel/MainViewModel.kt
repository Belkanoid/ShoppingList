package com.belkanoid.shoppinglist.presentation.shoppingList.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.belkanoid.shoppinglist.data.repositoryImpl.ShoppingListRepositoryImpl
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.useCases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShoppingListRepositoryImpl(application)

    private val getShoppingListUseCase = getShoppingListUseCase(repository)
    private val getShoppingDeleteUseCase = deleteShoppingItemUseCase(repository)
    private val getUpdateShoppingItemUseCase = updateShoppingItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    val shoppingList : LiveData<List<ShoppingItem>> = getShoppingListUseCase.execute()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        scope.launch {
            getShoppingDeleteUseCase.execute(shoppingItem)
        }
    }

    fun updateShoppingItem(shoppingItem: ShoppingItem) {
        scope.launch {
            val updatedShoppingItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
            getUpdateShoppingItemUseCase.execute(updatedShoppingItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}