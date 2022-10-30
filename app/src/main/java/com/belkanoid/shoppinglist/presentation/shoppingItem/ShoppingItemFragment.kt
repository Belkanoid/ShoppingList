package com.belkanoid.shoppinglist.presentation.shoppingItem

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.belkanoid.shoppinglist.R
import com.belkanoid.shoppinglist.databinding.FragmentShoppingItemBinding
import com.belkanoid.shoppinglist.domain.entity.ShoppingItem.Companion.UNDEFINED_ID
import com.belkanoid.shoppinglist.presentation.shoppingItem.viewModel.ShoppingItemViewModel


class ShoppingItemFragment : Fragment() {

    private lateinit var binding :FragmentShoppingItemBinding
    private val shoppingItemViewModel by lazy {
        ViewModelProvider(this)[ShoppingItemViewModel::class.java]
    }


    private var screenMode: String = UNDEFINED_SCREEN_MODE
    private var shoppingItemId: Int = UNDEFINED_ID
    private lateinit var onEditingFinishListener: OnEditingFinishListener



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishListener)
            onEditingFinishListener = context
        else
            throw RuntimeException("activity not implemented OnEditingFinishListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        launchRightMode()
        observeViewModel()
    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(SCREEN_MODE))
            throw RuntimeException("screen mode is absent")

        screenMode = args.getString(SCREEN_MODE)!!
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOPPING_ITEM_ID))
                throw RuntimeException("shopping id is absent")
            shoppingItemId = args.getInt(SHOPPING_ITEM_ID, UNDEFINED_ID)
        }
    }

    private fun observeViewModel() {

        shoppingItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.shoppingItemWrapperName.error = message
        }
        shoppingItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            binding.shoppingItemWrapperCount.error = message
        }

        binding.shoppingItemName.doOnTextChanged { _, _, _, _ ->
            shoppingItemViewModel.resetErrorInputName()
        }
        binding.shoppingItemCount.doOnTextChanged {  _, _, _, _ ->
            shoppingItemViewModel.resetErrorInputCount()
        }

        shoppingItemViewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishListener.onFinish()
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
        shoppingItemViewModel.shoppingItem.observe(viewLifecycleOwner) {
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


    interface OnEditingFinishListener {

        fun onFinish()

    }
    companion object {

        private const val SCREEN_MODE = "screen_mode"
        private const val SHOPPING_ITEM_ID = "shopping_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        private const val UNDEFINED_SCREEN_MODE = ""


        fun newInstanceAddMode() = ShoppingItemFragment().apply {
            arguments = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
        }

        fun newInstanceEditMode(shoppingItemId: Int) =
            ShoppingItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOPPING_ITEM_ID, shoppingItemId)
                }
            }
    }

}