package com.rumosoft.photogallery

import com.rumosoft.photogallery.data.network.apimodels.ApiImage

fun sampleImage(): ApiImage = ApiImage(
        albumId = 1L,
        id = 1L,
        title = "title",
        url = "url of the image",
        thumbnailUrl = "url of the thumbnail"
)