package com.rumosoft.feature_images.presentation.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.RemoveImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.StoreImageFromContentUseCase
import com.rumosoft.feature_images.infrastructure.Resource
import com.rumosoft.feature_images.infrastructure.StateApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val storeImageFromContentUseCase: StoreImageFromContentUseCase,
    private val removeImageUseCase: RemoveImageUseCase,
) : ViewModel() {
    private val _images = MutableStateFlow<StateApi<List<Image>>>(StateApi.Loading)
    val images: StateFlow<StateApi<List<Image>>> = _images

    private val _imagePicked = MutableStateFlow<String?>(null)
    val imagePicked: StateFlow<String?> = _imagePicked

    private val _imageRemoveResult = MutableStateFlow<StateApi<Unit>>(StateApi.Loading)
    val imageRemoveResult: StateFlow<StateApi<Unit>> = _imageRemoveResult

    fun getImages() {
        viewModelScope.launch {
            _images.emit(StateApi.Loading)
            when (val apiResponse = getImagesUseCase()) {
                is Resource.Success -> {
                    Timber.d("Received response: ${apiResponse.data?.take(3)}...")
                    _images.emit(StateApi.Success(apiResponse.data!!))
                }
                is Resource.Error -> {
                    Timber.d("Could not get a response: ${apiResponse.throwable}")
                    _images.emit(StateApi.Error(apiResponse.throwable))
                }
            }
        }
    }

    fun onImagePicked(uri: Uri) {
        viewModelScope.launch {
            storeImageFromContentUseCase(uri, null, null).let { newImage ->
                _imagePicked.emit(newImage)
            }
        }
    }

    fun restoreImagePicked() {
        viewModelScope.launch {
            _imagePicked.emit(null)
        }
    }

    fun removeImage(image: Image) {
        viewModelScope.launch {
            _imageRemoveResult.emit(StateApi.Loading)
            when (val apiResponse = removeImageUseCase(image)) {
                is Resource.Success -> {
                    _imageRemoveResult.emit(StateApi.Success(Unit))
                }
                is Resource.Error -> {
                    _imageRemoveResult.emit(StateApi.Error(apiResponse.throwable))
                }
            }
        }
    }

    fun restoreImageRemoved() {
        _imageRemoveResult.value = StateApi.Loading
    }
}
