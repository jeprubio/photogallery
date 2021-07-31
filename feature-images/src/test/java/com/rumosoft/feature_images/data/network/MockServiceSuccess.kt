package com.rumosoft.feature_images.data.network

import com.rumosoft.feature_images.Samples.sampleApiId
import com.rumosoft.feature_images.Samples.sampleApiImage
import com.rumosoft.feature_images.data.network.apimodels.ApiId
import com.rumosoft.feature_images.data.network.apimodels.ApiImage
import com.rumosoft.feature_images.data.network.apimodels.ApiTitle

class MockServiceSuccess : ImagesService {
    override suspend fun loadImages(): List<ApiImage> = listOf(sampleApiImage())
    override suspend fun addImage(image: ApiImage): ApiId = sampleApiId()

    override suspend fun editImage(photoId: Long, image: ApiImage): ApiId = sampleApiId()

    override suspend fun updateTitleImage(photoId: Long, title: ApiTitle): ApiImage =
        sampleApiImage()

    override suspend fun removeImage(photoId: Long) {
        return
    }
}
