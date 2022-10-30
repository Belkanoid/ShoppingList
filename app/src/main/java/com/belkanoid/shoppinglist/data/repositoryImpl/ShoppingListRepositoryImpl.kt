package com.belkanoid.shoppinglist.data.repositoryImpl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.belkanoid.shoppinglist.data.database.ShoppingListDatabase
import com.belkanoid.shoppinglist.data.database.dao.ShoppingListDao
import com.belkanoid.shoppinglist.data.database.mapper.ShoppingListMapper
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem.Companion.UNDEFINED_ID
import com.belkanoid.shoppinglist.domain.repository.ShoppingListRepository
import java.lang.RuntimeException
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

class ShoppingListRepositoryImpl @Inject constructor(
    private val mapper: ShoppingListMapper,
    private val shoppingDao : ShoppingListDao
) : ShoppingListRepository {


    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.addShoppingItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem.id)
    }

    override suspend fun updateShoppingItem(shoppingItem: ShoppingItem) {
        addShoppingItem(shoppingItem)
    }

    override suspend fun getShoppingItem(shoppingItemId: Int): ShoppingItem {
        val dbModel = shoppingDao.getShoppingItem(shoppingItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> =
        Transformations.map(shoppingDao.getShoppingList()) {
            mapper.mapListDbModelToListEntity(it)
        }


}