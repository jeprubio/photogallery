package com.rumosoft.photogallery.data.repository

import com.rumosoft.photogallery.data.network.ImagesNetwork
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
        private val imagesNetwork: ImagesNetwork,
) : ImagesRepository {
    override suspend fun getImages(): Resource<List<Image>?> = imagesNetwork.getImages()
}