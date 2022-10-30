package com.belkanoid.shoppinglist.presentation.shoppingList.adapter.utils

import androidx.recyclerview.widget.DiffUtil
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem

class ShoppingItemDiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {
    override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem == newItem
    }
}