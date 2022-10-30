package com.belkanoid.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.belkanoid.shoppinglist.presentation.shoppingItem.viewModel.ShoppingItemViewModel
import com.belkanoid.shoppinglist.presentation.shoppingList.viewModel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindsMainViewModel(viewModel: MainViewModel) : ViewModel

    @IntoMap
    @ViewModelKey(ShoppingItemViewModel::class)
    @Binds
    fun bindsShoppingItemViewModel(viewModel: ShoppingItemViewModel) : ViewModel
}