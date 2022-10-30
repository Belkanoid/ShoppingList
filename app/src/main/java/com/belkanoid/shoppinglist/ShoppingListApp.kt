package com.belkanoid.shoppinglist

import android.app.Application
import com.belkanoid.shoppinglist.di.DaggerShoppingListComponent

class ShoppingListApp : Application() {

    val component by lazy {
        DaggerShoppingListComponent.factory().create(this)
    }
}