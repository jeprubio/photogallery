package com.rumosoft.feature_images

import com.rumosoft.feature_images.data.network.apimodels.ApiId
import com.rumosoft.feature_images.data.network.apimodels.ApiImage

object Samples {
    fun sampleApiImage(): ApiImage = ApiImage(
            albumId = 1L,
            id = 1L,
            title = "title",
            url = "url of the image",
            thumbnailUrl = "url of the thumbnail",
    )

    fun sampleApiId(): ApiId = ApiId(
            id = 1L,
    )
}