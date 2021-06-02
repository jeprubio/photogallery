package com.rumosoft.feature_images.presentation.gallery

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rumosoft.feature_images.R
import com.rumosoft.feature_images.databinding.ChoosePictureSourceBottomsheetBinding
import com.rumosoft.feature_images.databinding.GalleryFragmentBinding
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.model.Image.Companion.NEW_IMAGE_ID
import com.rumosoft.feature_images.infrastructure.StateApi
import com.rumosoft.feature_images.infrastructure.extensions.*
import com.rumosoft.feature_images.presentation.adapters.ImagesAdapter
import com.rumosoft.feature_images.presentation.listeners.ClickListener
import com.rumosoft.feature_images.presentation.listeners.ImageClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var binding: GalleryFragmentBinding
    private val imagesAdapter = ImagesAdapter(
            editClickListener = ImageClickListener { image ->
                showImageDetails(image)
            },
            deleteClickListener = ImageClickListener { image ->
                viewModel.removeImage(image)
            },
    )
    private var bottomSheetPictureChooser: BottomSheetDialog? = null
    private var imageUri: Uri? = null
    private val capturePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri?.also {
                Timber.d("Got image at: $it")
                viewModel.onImagePicked(it)
            }
        }
    }
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.also { it ->
            Timber.d("Picture picked: $it")
            viewModel.onImagePicked(it)
        }
    }

    companion object {
        const val IMAGE_INPUT = "image/*"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<GalleryFragmentBinding>(
                inflater, R.layout.gallery_fragment, container, false
        ).also {
            it.lifecycleOwner = this
        }
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_picture -> {
                Timber.d("Clicked add picture")
                showImageChooser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showImageChooser() {
        val pictureChooserView = initBottomSheetContent()
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(pictureChooserView)
        bottomSheetDialog.show()
        bottomSheetPictureChooser = bottomSheetDialog
    }

    private fun init() {
        setLayoutManagerAndAdapter(imagesAdapter)
        performFirstSearch()
        observeImages()
        observeImagePicked()
        observeRemovalResult()
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
                is StateApi.Success -> {
                    hideLoading()
                    loadedImages(it.data)
                }
                is StateApi.Error -> {
                    hideLoading()
                    showError(it.throwable)
                }
            }
        }
    }

    private fun observeImagePicked() {
        viewModel.imagePicked.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val image = Image(NEW_IMAGE_ID, "", it, "")
                showImageDetails(image)
                viewModel.restoreImagePicked()
            }
        }
    }

    private fun showLoading() {
        binding.galleryLoading.show()
    }

    private fun hideLoading() {
        binding.galleryLoading.hide()
    }

    private fun loadedImages(imagesList: List<Image>) {
        imagesAdapter.data = imagesList
    }

    private fun showImageDetails(image: Image) {
        this.findNavController().navigate(
                GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(
                        Gson().toJson(image)
                )
        )
    }

    private fun showError(throwable: Throwable) {
        // TODO use the throwable to set an appropriate message?
        Toast.makeText(requireContext(), getString(R.string.error_loading_images), Toast.LENGTH_SHORT).show()
    }

    private fun initBottomSheetContent(): View {
        val choosePictureSourceView = layoutInflater.inflate(R.layout.choose_picture_source_bottomsheet, null) as ViewGroup
        val binding = ChoosePictureSourceBottomsheetBinding.inflate(layoutInflater, choosePictureSourceView, false)
        setBottomSheetListeners(binding)
        return binding.root
    }

    private fun setBottomSheetListeners(binding: ChoosePictureSourceBottomsheetBinding) {
        setBottomsheetOptionLaunchCameraListener(binding)
        setBottomsheetOptionLaunchGalleryListener(binding)
        setBottomsheetOptionCancelListener(binding)
    }

    private fun setBottomsheetOptionLaunchCameraListener(binding: ChoosePictureSourceBottomsheetBinding) {
        binding.clickFromCamera = ClickListener {
            askForCameraPermissions {
                takePictureFromCamera()
            }
            bottomSheetPictureChooser?.dismiss()
        }
    }

    private fun setBottomsheetOptionLaunchGalleryListener(binding: ChoosePictureSourceBottomsheetBinding) {
        binding.clickFromGallery = ClickListener {
            pickPictureFromGallery()
            bottomSheetPictureChooser?.dismiss()
        }
    }

    private fun setBottomsheetOptionCancelListener(binding: ChoosePictureSourceBottomsheetBinding) {
        binding.clickCancel = ClickListener {
            bottomSheetPictureChooser?.dismiss()
        }
    }

    private fun takePictureFromCamera() {
        context?.run {
            imageUri = FileProvider.getUriForFile(
                    this,
                    applicationContext.packageName + ".fileprovider",
                    createImageFile()
            )
            capturePhoto.launch(imageUri)
        }
    }

    private fun pickPictureFromGallery() {
        pickImage.launch(IMAGE_INPUT)
    }

    private fun observeRemovalResult() {
        viewModel.imageRemoveResult.observe(viewLifecycleOwner) {
            when (it) {
                is StateApi.Loading -> { /* Do something? */
                }
                is StateApi.Success -> {
                    onImageRemoved()
                }
                is StateApi.Error -> {
                    showErrorDialog()
                }
            }
        }
    }

    private fun onImageRemoved() {
        Timber.d("The api returned successful response")
        binding.root.snack(R.string.success, Snackbar.LENGTH_SHORT) { }
        viewModel.restoreImageRemoved()
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