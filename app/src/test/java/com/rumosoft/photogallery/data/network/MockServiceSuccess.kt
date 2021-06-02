package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.data.network.apimodels.ApiId
import com.rumosoft.photogallery.data.network.apimodels.ApiImage
import com.rumosoft.photogallery.data.network.apimodels.ApiTitle
import com.rumosoft.photogallery.Samples.sampleApiId
import com.rumosoft.photogallery.Samples.sampleApiImage

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