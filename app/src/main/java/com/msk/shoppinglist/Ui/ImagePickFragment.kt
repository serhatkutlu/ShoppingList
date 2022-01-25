package com.msk.shoppinglist.Ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msk.shoppinglist.R

class ImagePickFragment:Fragment(R.layout.fragment_image_pick){
    lateinit var viewModel: ViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(viewModel::class.java)
    }
}