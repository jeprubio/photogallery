package com.rumosoft.feature_images.domain.usecases.interfaces.repository

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.infrastructure.Resource

interface ImagesRepository {
    suspend fun getImages(): Resource<List<Image>?>

    suspend fun addImage(image: Image): Resource<Long>

    suspend fun editImage(image: Image): Resource<Long>

    suspend fun updateImageTitle(image: Image): Resource<Image>

    suspend fun removeImage(image: Image): Resource<Unit>
}