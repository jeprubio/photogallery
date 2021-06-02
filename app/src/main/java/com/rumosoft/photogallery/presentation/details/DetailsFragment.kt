package com.rumosoft.photogallery.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rumosoft.photogallery.R
import com.rumosoft.photogallery.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<DetailsFragmentBinding>(
                inflater, R.layout.details_fragment, container, false
        ).also {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }
        init()
        return binding.root
    }

    private fun init() {
        viewModel.image.value?.id?.takeIf { it > 0 }?.also {
            binding.itemEditButton.text = getString(R.string.edit_image)
        } ?: run {
            binding.itemEditButton.text = getString(R.string.add_image)
        }
    }
}