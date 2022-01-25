package com.msk.shoppinglist.Ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msk.shoppinglist.R

class AddShoppingItemFragment:Fragment(R.layout.fragment_add_shopping_item) {
    lateinit var viewModel: ViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(viewModel::class.java)
    }
}