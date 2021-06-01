package com.rumosoft.photogallery.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.photogallery.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.photogallery.infrastructure.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
        private val getImagesUseCase: GetImagesUseCase,
) : ViewModel() {

    fun getImages() {
        viewModelScope.launch {
            when (val apiResponse = getImagesUseCase()) {
                is Resource.Success -> {
                    Timber.d("Received response: ${apiResponse.data?.take(3)}...")
                }
                is Resource.Error -> {
                    Timber.d("Could not get a response: ${apiResponse.throwable}")
                }
            }
        }
    }
}