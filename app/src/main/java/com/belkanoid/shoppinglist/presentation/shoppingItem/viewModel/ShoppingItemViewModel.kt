package com.belkanoid.shoppinglist.presentation.shoppingItem.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belkanoid.shoppinglist.data.repositoryImpl.ShoppingListRepositoryImpl
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.useCases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShoppingItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShoppingListRepositoryImpl(application)
    private val getAddShoppingItemUseCase = addShoppingItemUseCase(repository)
    private val getUpdateShoppingItemUseCase = updateShoppingItemUseCase(repository)
    private val getShoppingItemUseCase = getShoppingItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _shoppingItem = MutableLiveData<ShoppingItem>()
    val shoppingItem : LiveData<ShoppingItem>
        get() = _shoppingItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen : LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShoppingItem(shoppingItemId : Int) {
        scope.launch {
            _shoppingItem.value = getShoppingItemUseCase.execute(shoppingItemId)

        }
    }

    fun addShoppingItem(inputName : String?, inputCount : String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val checkedInput = validateInput(name = name, count = count)
        if(checkedInput) {
            val shoppingItem = ShoppingItem(name, count, true)
            scope.launch {
                getAddShoppingItemUseCase.execute(shoppingItem)

            }
            finishWork()
        }
    }

    fun updateShoppingItem(inputName : String?, inputCount : String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val checkedInput = validateInput(name = name, count = count)
        if(checkedInput) {
            _shoppingItem.value?.let {
                val shoppingItem = it.copy(name = name, count = count)
                scope.launch {
                    getUpdateShoppingItemUseCase.execute(shoppingItem)
                }
                finishWork()
            }

        }
    }


    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e : Exception) {
            0
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun validateInput(name : String, count : Int) : Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName() { _errorInputName.value = false}
    fun resetErrorInputCount() { _errorInputCount.value = false}

    fun finishWork() { _shouldCloseScreen.value = Unit}

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}