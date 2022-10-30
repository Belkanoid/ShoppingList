package com.belkanoid.shoppinglist.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.belkanoid.shoppinglist.data.database.dbEntity.ShoppingItemDbModel

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shopping_item")
    fun getShoppingList() : LiveData<List<ShoppingItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingItem(shoppingItem: ShoppingItemDbModel)

    @Query("DELETE FROM shopping_item WHERE id=:shoppingItemId")
    suspend fun deleteShoppingItem(shoppingItemId : Int)

    @Query("SELECT * FROM shopping_item WHERE id=:shoppingItemId LIMIT 1")
    suspend fun getShoppingItem(shoppingItemId: Int) : ShoppingItemDbModel
}