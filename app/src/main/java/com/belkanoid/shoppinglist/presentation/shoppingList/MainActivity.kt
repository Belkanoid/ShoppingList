package com.belkanoid.shoppinglist.presentation.shoppingList

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.belkanoid.shoppinglist.databinding.ActivityMainBinding
import com.belkanoid.shoppinglist.presentation.shoppingItem.ShoppingItemActivity
import com.belkanoid.shoppinglist.presentation.shoppingItem.ShoppingItemFragment
import com.belkanoid.shoppinglist.presentation.shoppingList.adapter.ShoppingAdapter
import com.belkanoid.shoppinglist.presentation.shoppingList.adapter.ShoppingAdapter.Companion.DISABLED_VIEW_TYPE
import com.belkanoid.shoppinglist.presentation.shoppingList.adapter.ShoppingAdapter.Companion.ENABLED_VIEW_TYPE
import com.belkanoid.shoppinglist.presentation.shoppingList.adapter.ShoppingAdapter.Companion.MAX_POOL_SIZE
import com.belkanoid.shoppinglist.presentation.shoppingList.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.shoppingList.observe(this) {
            shoppingAdapter.submitList(it)
        }
        setRecyclerView()

        binding.shoppingAddButton.setOnClickListener{
            if (isPaneOrientation()) {
                val intent = ShoppingItemActivity.newIntentAddMode(this@MainActivity)
                startActivity(intent)
            } else attachFragment(ShoppingItemFragment.newInstanceAddMode())

        }


    }

    private fun isPaneOrientation() : Boolean{
        return binding.containerShoppingItem == null
    }

    private fun attachFragment(fragment: ShoppingItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(binding.containerShoppingItem!!.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setRecyclerView() {
        binding.shoppingListRecyclerview.apply {
            shoppingAdapter = ShoppingAdapter()
            adapter = shoppingAdapter
            recycledViewPool.setMaxRecycledViews(ENABLED_VIEW_TYPE, MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(
                DISABLED_VIEW_TYPE, MAX_POOL_SIZE
            )
        }

        shoppingAdapter.apply {
            onShoppingItemOnLongClickListener = {
                mainViewModel.updateShoppingItem(it)
            }
            onShoppingItemOnClickListener = {
                if (isPaneOrientation()) {
                    val intent = ShoppingItemActivity.newIntentEditMode(this@MainActivity, it.id)
                    startActivity(intent)
                } else attachFragment(ShoppingItemFragment.newInstanceEditMode(it.id))


            }
        }
        simpleSwipeCallback()

    }

    private fun simpleSwipeCallback() {

        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shoppingItem = shoppingAdapter.currentList[viewHolder.adapterPosition]
                mainViewModel.deleteShoppingItem(shoppingItem)
            }

        }

        ItemTouchHelper(callback).attachToRecyclerView(binding.shoppingListRecyclerview)
    }

}