package com.rumosoft.photogallery.data.network.mappers

import com.rumosoft.photogallery.data.network.apimodels.ApiImage
import com.rumosoft.photogallery.domain.model.Image

fun ApiImage.toImage(): Image {
    return Image(
            id = id,
            title = title ?: "",
            image = url ?: "",
            thumbnail = thumbnailUrl ?: ""
    )
}