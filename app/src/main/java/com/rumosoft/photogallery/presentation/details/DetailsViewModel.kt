package com.rumosoft.photogallery.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.rumosoft.photogallery.domain.model.Image
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
        savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _image = MutableLiveData<Image>()
    val image: LiveData<Image> = _image

    init {
        val json = savedStateHandle.get<String>("item")
        _image.value = Gson().fromJson(json, Image::class.java)
    }
}