package com.rumosoft.photogallery.presentation.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.photogallery.infrastructure.Resource
import com.rumosoft.photogallery.infrastructure.StateApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
        private val getImagesUseCase: GetImagesUseCase,
) : ViewModel() {
    private val _images = MutableLiveData<StateApi<List<Image>>>()
    val images: LiveData<StateApi<List<Image>>> = _images

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

    fun onImageClicked(image: Image) {
        // Todo
    }
}