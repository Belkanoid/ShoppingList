package com.belkanoid.shoppinglist.data.database.mapper

import com.belkanoid.shoppinglist.data.database.dbEntity.ShoppingItemDbModel
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import javax.inject.Inject

class ShoppingListMapper @Inject constructor() {

    fun mapEntityToDbModel(shoppingItem: ShoppingItem) = ShoppingItemDbModel(
        id = shoppingItem.id,
        name = shoppingItem.name,
        count = shoppingItem.count,
        enabled = shoppingItem.enabled
    )

    fun mapDbModelToEntity(shoppingItemDbModel: ShoppingItemDbModel) = ShoppingItem(
        id = shoppingItemDbModel.id,
        name = shoppingItemDbModel.name,
        count = shoppingItemDbModel.count,
        enabled = shoppingItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<ShoppingItemDbModel>) = list.map {
            mapDbModelToEntity(it)
        }


}