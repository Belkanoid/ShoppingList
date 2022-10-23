package com.belkanoid.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.belkanoid.shoppinglist.domain.model.ShoppingItem
import com.belkanoid.shoppinglist.domain.model.ShoppingItem.Companion.UNDEFINED_ID
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import java.lang.RuntimeException
import kotlin.math.abs
import kotlin.random.Random

object ShoppingListRepositoryImpl : ShoppingListRepository{

    private val _shoppingList = sortedSetOf<ShoppingItem>({e1, e2 -> e1.id.compareTo(e2.id) })
    private val observableShoppingList : MutableLiveData<List<ShoppingItem>> = MutableLiveData()

    private var autoIncrementId = 0

    init {
        for(i in 0..10) {
            val shoppingItem = ShoppingItem(
                name = "name $i",
                count = abs(Random.nextInt()) % 5,
                enabled = Random.nextBoolean()
            )
            addShoppingItem(shoppingItem)
        }
    }


    override fun addShoppingItem(shoppingItem: ShoppingItem) {
        if(shoppingItem.id == UNDEFINED_ID)
            shoppingItem.id = autoIncrementId++

        _shoppingList.add(shoppingItem)
        updateShoppingList()
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        _shoppingList.remove(shoppingItem)
        updateShoppingList() 
    }

    override fun updateShoppingItem(shoppingItem: ShoppingItem) {
        val oldElement = getShoppingItem(shoppingItem.id)
        _shoppingList.remove(oldElement)
        addShoppingItem(shoppingItem)
    }

    override fun getShoppingItem(shoppingItemId: Int): ShoppingItem {
        return  _shoppingList.find {
            it.id == shoppingItemId
        } ?: throw RuntimeException("Element with id=$shoppingItemId not found")
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> {
        return observableShoppingList
    }

    private fun updateShoppingList() {
        observableShoppingList.value = _shoppingList.toList()
    }


}