package com.rumosoft.feature_images.data.network.mappers

import com.rumosoft.feature_images.data.network.apimodels.ApiImage
import com.rumosoft.feature_images.domain.model.Image

fun ApiImage.toImage(): Image {
    return Image(
        id = id,
        title = title ?: "",
        image = url ?: "",
        thumbnail = thumbnailUrl ?: ""
    )
}
