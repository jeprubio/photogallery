package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.data.network.apimodels.ApiId
import com.rumosoft.photogallery.data.network.apimodels.ApiImage
import com.rumosoft.photogallery.data.network.apimodels.ApiTitle

class MockServiceError : ImagesService {
    override suspend fun loadImages(): List<ApiImage> = throw Exception("Load Images Exception")
    override suspend fun addImage(image: ApiImage): ApiId =
            throw Exception("Add Image Exception")

    override suspend fun editImage(photoId: Long, image: ApiImage): ApiId =
            throw Exception("Edit Image Exception")

    override suspend fun updateTitleImage(photoId: Long, title: ApiTitle): ApiImage =
            throw Exception("Update Title Exception")

    override suspend fun removeImage(photoId: Long): Unit =
            throw Exception("Remove Image Exception")
}