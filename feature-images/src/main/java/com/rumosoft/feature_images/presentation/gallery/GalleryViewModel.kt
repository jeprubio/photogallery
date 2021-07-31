package com.rumosoft.feature_images.presentation.gallery

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.RemoveImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.StoreImageFromContentUseCase
import com.rumosoft.feature_images.infrastructure.Resource
import com.rumosoft.feature_images.infrastructure.StateApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val storeImageFromContentUseCase: StoreImageFromContentUseCase,
    private val removeImageUseCase: RemoveImageUseCase,
) : ViewModel() {
    private val _images = MutableLiveData<StateApi<List<Image>>>()
    val images: LiveData<StateApi<List<Image>>> = _images

    private val _imagePicked = MutableLiveData<String?>()
    val imagePicked: LiveData<String?> = _imagePicked

    private val _imageRemoveResult = MutableLiveData<StateApi<Unit>>()
    val imageRemoveResult: LiveData<StateApi<Unit>> = _imageRemoveResult

    fun getImages() {
        viewModelScope.launch {
            _images.value = StateApi.Loading
            when (val apiResponse = getImagesUseCase()) {
                is Resource.Success -> {
                    Timber.d("Received response: ${apiResponse.data?.take(3)}...")
                    _images.value = StateApi.Success(apiResponse.data!!)
                }
                is Resource.Error -> {
                    Timber.d("Could not get a response: ${apiResponse.throwable}")
                    _images.value = StateApi.Error(apiResponse.throwable)
                }
            }
        }
    }

    fun onImagePicked(uri: Uri) {
        viewModelScope.launch {
            storeImageFromContentUseCase(uri, null, null).let { newImage ->
                _imagePicked.value = newImage
            }
        }
    }

    fun restoreImagePicked() {
        _imagePicked.value = null
    }

    fun removeImage(image: Image) {
        viewModelScope.launch {
            _imageRemoveResult.value = StateApi.Loading
            when (val apiResponse = removeImageUseCase(image)) {
                is Resource.Success -> {
                    _imageRemoveResult.value = StateApi.Success(Unit)
                }
                is Resource.Error -> {
                    _imageRemoveResult.value = StateApi.Error(apiResponse.throwable)
                }
            }
        }
    }

    fun restoreImageRemoved() {
        _imageRemoveResult.value = null
    }
}
