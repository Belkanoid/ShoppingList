package com.belkanoid.shoppinglist.di

import android.app.Application
import com.belkanoid.shoppinglist.presentation.shoppingItem.ShoppingItemFragment
import com.belkanoid.shoppinglist.presentation.shoppingList.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface ShoppingListComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: ShoppingItemFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
        ) : ShoppingListComponent
    }

}