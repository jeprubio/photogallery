package com.rumosoft.feature_images.presentation.details

import android.text.Editable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.AddImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.UpdateImageTitleUseCase
import com.rumosoft.feature_images.infrastructure.Resource
import com.rumosoft.feature_images.infrastructure.StateApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val addImageUseCase: AddImageUseCase,
    private val updateImageTitleUseCase: UpdateImageTitleUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _image = MutableStateFlow<Image?>(null)
    val image: StateFlow<Image?> = _image

    private val _imageUpdateResult = MutableStateFlow<StateApi<Unit>>(StateApi.Loading)
    val imageUpdateResult: StateFlow<StateApi<Unit>> = _imageUpdateResult

    init {
        val json = savedStateHandle.get<String>("item")
        viewModelScope.launch {
            _image.emit(Gson().fromJson(json, Image::class.java))
        }
    }

    fun afterTitleChanged(editable: Editable) {
        viewModelScope.launch {
            _image.emit(_image.value?.copy(title = editable.toString()))
        }
    }

    fun saveImage() {
        viewModelScope.launch {
            _imageUpdateResult.emit(StateApi.Loading)
            val title = _image.value!!.title
            val image = _image.value!!.copy(image = "https://$title/", thumbnail = "https://$title/min")
            when (
                val apiResponse = _image.value?.id?.takeIf { it > 0 }?.let {
                    updateImageTitleUseCase(image)
                } ?: run {
                    addImageUseCase(image)
                }
            ) {
                is Resource.Success -> {
                    _imageUpdateResult.emit(StateApi.Success(Unit))
                }
                is Resource.Error -> {
                    _imageUpdateResult.emit(StateApi.Error(apiResponse.throwable))
                }
            }
        }
    }
}
