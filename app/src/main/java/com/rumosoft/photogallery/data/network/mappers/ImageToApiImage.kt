package com.rumosoft.photogallery.data.network.mappers

import com.rumosoft.photogallery.data.network.apimodels.ApiImage
import com.rumosoft.photogallery.domain.model.Image

fun Image.toApiImage(): ApiImage {
    return ApiImage(
            albumId = 0L,
            id = id,
            title = title,
            url = image,
            thumbnailUrl = thumbnail,
    )
}