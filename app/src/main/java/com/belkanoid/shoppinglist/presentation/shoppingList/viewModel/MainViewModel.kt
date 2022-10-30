package com.belkanoid.shoppinglist.presentation.shoppingList.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belkanoid.shoppinglist.data.repositoryImpl.ShoppingListRepositoryImpl
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.useCases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShoppingListUseCase: getShoppingListUseCase,
    private val getShoppingDeleteUseCase: deleteShoppingItemUseCase,
    private val getUpdateShoppingItemUseCase: updateShoppingItemUseCase,
) : ViewModel() {

    val shoppingList: LiveData<List<ShoppingItem>> = getShoppingListUseCase.execute()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            getShoppingDeleteUseCase.execute(shoppingItem)
        }
    }

    fun updateShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            val updatedShoppingItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
            getUpdateShoppingItemUseCase.execute(updatedShoppingItem)
        }
    }

}