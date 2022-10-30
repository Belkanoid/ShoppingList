package com.belkanoid.shoppinglist.presentation.shoppingList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.belkanoid.shoppinglist.R
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem
import com.belkanoid.shoppinglist.presentation.shoppingList.adapter.utils.ShoppingItemDiffCallback
import java.lang.RuntimeException

class ShoppingAdapter : ListAdapter<ShoppingItem, ShoppingItemHolder>(ShoppingItemDiffCallback()) {

    var onShoppingItemOnLongClickListener : ((ShoppingItem) -> Unit)? = null
    var onShoppingItemOnClickListener : ((ShoppingItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemHolder {
        val layout = when(viewType) {
            ENABLED_VIEW_TYPE -> R.layout.shopping_list_item_enabled
            DISABLED_VIEW_TYPE -> R.layout.shopping_list_item_disabled
            else -> throw RuntimeException("viewType $viewType doesn\'t found")
        }
        val itemView = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ShoppingItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShoppingItemHolder, position: Int) {
        val shoppingItem = getItem(position)
        holder.onBind(shoppingItem)

        holder.itemView.apply {
            setOnClickListener{
                onShoppingItemOnClickListener?.invoke(shoppingItem)
            }
            setOnLongClickListener{
                onShoppingItemOnLongClickListener?.invoke(shoppingItem)
                true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shoppingItem = getItem(position)
        return when(shoppingItem.enabled) {
            true -> ENABLED_VIEW_TYPE
            else -> DISABLED_VIEW_TYPE
        }
    }

    companion object {
        const val DISABLED_VIEW_TYPE = 100
        const val ENABLED_VIEW_TYPE = 101
        const val MAX_POOL_SIZE = 15

    }

}