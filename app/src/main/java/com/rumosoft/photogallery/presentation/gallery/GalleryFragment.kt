package com.rumosoft.photogallery.presentation.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rumosoft.photogallery.R
import com.rumosoft.photogallery.databinding.GalleryFragmentBinding
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.StateApi
import com.rumosoft.photogallery.presentation.adapters.ImageClickListener
import com.rumosoft.photogallery.presentation.adapters.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var binding: GalleryFragmentBinding
    private val imagesAdapter = ImagesAdapter(ImageClickListener { itemId ->
        viewModel.onImageClicked(itemId)
    })

    companion object {
        fun newInstance() = GalleryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.gallery_fragment, container, false
        )
        binding.lifecycleOwner = this
        init()
        return binding.root
    }

    private fun init() {
        setLayoutManagerAndAdapter(imagesAdapter)
        performFirstSearch()
        observeImages()
    }

    private fun setLayoutManagerAndAdapter(imagesAdapter: ImagesAdapter) {
        val gridColumnCount = resources.getInteger(R.integer.grid_column_count)
        binding.recycler.apply {
            setHasFixedSize(false)
            layoutManager = StaggeredGridLayoutManager(gridColumnCount, 1)
            adapter = imagesAdapter
        }
    }

    private fun performFirstSearch() {
        viewModel.getImages()
    }

    private fun observeImages() {
        viewModel.images.observe(viewLifecycleOwner) {
            when (it) {
                is StateApi.Loading -> showLoading()
                is StateApi.Success -> loadedImages(it.data)
                is StateApi.Error -> showError(it.throwable)
            }
        }
    }

    private fun showLoading() {
        // TODO
    }

    private fun loadedImages(imagesList: List<Image>) {
        imagesAdapter.data = imagesList
    }

    private fun showError(throwable: Throwable) {
        // TODO use the throwable to set an appropriate message?
        Toast.makeText(requireContext(), getString(R.string.error_loading_images), Toast.LENGTH_SHORT).show()
    }
}