package com.belkanoid.shoppinglist.presentation.shoppingList.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belkanoid.shoppinglist.R
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem

class ShoppingItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val shoppingItemName : TextView = itemView.findViewById(R.id.shopping_name)
    private val shoppingItemCount : TextView = itemView.findViewById(R.id.shopping_count)

    fun onBind(shoppingItem: ShoppingItem) {
        shoppingItemName.text = shoppingItem.name
        shoppingItemCount.text = shoppingItem.count.toString()
    }

}