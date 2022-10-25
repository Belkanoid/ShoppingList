package com.belkanoid.shoppinglist.presentation.shoppingItem

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.belkanoid.shoppinglist.R
import com.belkanoid.shoppinglist.databinding.ActivityShoppingItemBinding
import com.belkanoid.shoppinglist.domain.model.ShoppingItem.Companion.UNDEFINED_ID
import com.belkanoid.shoppinglist.presentation.shoppingItem.viewModel.ShoppingItemViewModel

class ShoppingItemActivity : AppCompatActivity(), ShoppingItemFragment.OnEditingFinishListener {
    private lateinit var binding: ActivityShoppingItemBinding

    private var screenMode: String = UNDEFINED_MODE
    private var shoppingItemId: Int = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null)
            launchRightMode()

    }

//

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> ShoppingItemFragment.newInstanceAddMode()
            MODE_EDIT -> ShoppingItemFragment.newInstanceEditMode(shoppingItemId)
            else -> throw RuntimeException("undefined screen mode: $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.container_shopping_item, fragment)
            .commit()


    }


    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE))
            throw RuntimeException("screen mode is absent")

        screenMode = intent.getStringExtra(EXTRA_SCREEN_MODE).toString()
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOPPING_ITEM_ID))
                throw RuntimeException("shopping id is absent")

            shoppingItemId = intent.getIntExtra(EXTRA_SHOPPING_ITEM_ID, UNDEFINED_ID)
        }

    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "screen_mode"
        private const val EXTRA_SHOPPING_ITEM_ID = "shopping_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        private const val UNDEFINED_MODE = ""


        fun newIntentAddMode(context: Context): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            }
            return intent
        }

        fun newIntentEditMode(context: Context, shoppingItemId: Int): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
                putExtra(EXTRA_SHOPPING_ITEM_ID, shoppingItemId)
            }
            return intent
        }
    }

    override fun onFinish() {
        finish()
    }
}