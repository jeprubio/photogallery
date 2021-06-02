package com.rumosoft.feature_images.data.network.mappers

import com.rumosoft.feature_images.data.network.apimodels.ApiImage
import com.rumosoft.feature_images.domain.model.Image

fun Image.toApiImage(): ApiImage {
    return ApiImage(
            albumId = 0L,
            id = id,
            title = title,
            url = image,
            thumbnailUrl = thumbnail,
    )
}