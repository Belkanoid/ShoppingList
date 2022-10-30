package com.belkanoid.shoppinglist.data.database.dbEntity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shopping_item")
data class ShoppingItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val count : Int,
    val enabled : Boolean
)
