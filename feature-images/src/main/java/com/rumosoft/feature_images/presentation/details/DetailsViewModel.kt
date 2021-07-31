package com.rumosoft.feature_images.presentation.details

import android.text.Editable
import androidx.lifecycle.*
import com.google.gson.Gson
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.AddImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.UpdateImageTitleUseCase
import com.rumosoft.feature_images.infrastructure.Resource
import com.rumosoft.feature_images.infrastructure.StateApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val addImageUseCase: AddImageUseCase,
    private val updateImageTitleUseCase: UpdateImageTitleUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _image = MutableLiveData<Image>()
    val image: LiveData<Image> = _image

    private val _imageUpdateResult = MutableLiveData<StateApi<Unit>>()
    val imageUpdateResult: LiveData<StateApi<Unit>> = _imageUpdateResult

    init {
        val json = savedStateHandle.get<String>("item")
        _image.value = Gson().fromJson(json, Image::class.java)
    }

    fun afterTitleChanged(editable: Editable) {
        _image.value = _image.value?.copy(title = editable.toString())
    }

    fun saveImage() {
        viewModelScope.launch {
            _imageUpdateResult.value = StateApi.Loading
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
                    _imageUpdateResult.value = StateApi.Success(Unit)
                }
                is Resource.Error -> {
                    _imageUpdateResult.value = StateApi.Error(apiResponse.throwable)
                }
            }
        }
    }
}
