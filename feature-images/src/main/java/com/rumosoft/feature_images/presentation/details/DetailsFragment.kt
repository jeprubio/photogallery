package com.rumosoft.feature_images.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.rumosoft.feature_images.R
import com.rumosoft.feature_images.databinding.DetailsFragmentBinding
import com.rumosoft.feature_images.infrastructure.StateApi
import com.rumosoft.feature_images.infrastructure.extensions.alert
import com.rumosoft.feature_images.infrastructure.extensions.positiveButton
import com.rumosoft.feature_images.infrastructure.extensions.snack
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
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
        setSaveButtonTitle()
        observeImageUpdateResult()
    }

    private fun setSaveButtonTitle() {
        viewModel.image.value?.id?.takeIf { it > 0 }?.also {
            binding.itemEditButton.text = getString(R.string.edit_image)
        } ?: run {
            binding.itemEditButton.text = getString(R.string.add_image)
        }
    }

    private fun observeImageUpdateResult() {
        viewModel.imageUpdateResult.observe(viewLifecycleOwner) { stateApi ->
            when (stateApi) {
                is StateApi.Success -> {
                    onImageUpdated()
                }
                is StateApi.Error -> {
                    showErrorDialog()
                }
                is StateApi.Loading -> { /* Do something? */
                }
            }
        }
    }

    private fun onImageUpdated() {
        Timber.d("The api returned successful response")
        binding.root.snack(R.string.success, Snackbar.LENGTH_SHORT) { }
    }

    private fun showErrorDialog() {
        Timber.d("The api returned error response")
        context?.alert {
            setMessage(getString(R.string.something_happened_error_message))
            positiveButton(getString(R.string.ok)) {
                it.dismiss()
            }
        }
    }
}