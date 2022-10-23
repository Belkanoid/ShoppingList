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

class ShoppingItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingItemBinding
    private val shoppingItemViewModel by lazy {
        ViewModelProvider(this)[ShoppingItemViewModel::class.java]
    }

    private var screenMode: String = UNDEFINED_MODE
    private var shoppingItemId: Int = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        launchRightMode()
        observeViewModel()

    }

    private fun observeViewModel() {

        shoppingItemViewModel.errorInputName.observe(this@ShoppingItemActivity) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.shoppingItemWrapperName.error = message
        }
        shoppingItemViewModel.errorInputCount.observe(this@ShoppingItemActivity) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            binding.shoppingItemWrapperCount.error = message
        }

        binding.shoppingItemName.doOnTextChanged { text, start, before, count ->
            shoppingItemViewModel.resetErrorInputName()
        }
        binding.shoppingItemCount.doOnTextChanged { text, start, before, count ->
            shoppingItemViewModel.resetErrorInputCount()
        }

        shoppingItemViewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
            else -> throw RuntimeException("undefined screen mode: $screenMode")
        }

    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            shoppingItemViewModel.addShoppingItem(
                binding.shoppingItemName.text.toString(),
                binding.shoppingItemCount.text.toString()
            )
        }
    }

    private fun launchEditMode() {
        shoppingItemViewModel.getShoppingItem(shoppingItemId)
        shoppingItemViewModel.shoppingItem.observe(this@ShoppingItemActivity) {
            binding.shoppingItemName.setText(it.name)
            binding.shoppingItemCount.setText(it.count.toString())
        }
        binding.saveButton.setOnClickListener {
            shoppingItemViewModel.updateShoppingItem(
                binding.shoppingItemName.text.toString(),
                binding.shoppingItemCount.text.toString()
            )
        }

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
}