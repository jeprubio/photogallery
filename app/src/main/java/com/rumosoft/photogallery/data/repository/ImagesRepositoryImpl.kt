package com.rumosoft.photogallery.data.repository

import com.rumosoft.photogallery.data.network.ImagesNetwork
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.photogallery.infrastructure.Resource
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
        private val imagesNetwork: ImagesNetwork,
) : ImagesRepository {
    override suspend fun getImages(): Resource<List<Image>?> = imagesNetwork.getImages()
    override suspend fun addImage(image: Image): Resource<Long> = imagesNetwork.addImage(image)

    override suspend fun editImage(image: Image): Resource<Long> = imagesNetwork.editImage(image)

    override suspend fun updateImageTitle(image: Image): Resource<Image> =
            imagesNetwork.updateImageTitle(image)

    override suspend fun removeImage(image: Image): Resource<Unit> =
            imagesNetwork.removeImage(image)
}