package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.data.network.mappers.toPicture
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ImagesNetworkImpl @Inject constructor(
    private val imagesService: ImagesService,
) : ImagesNetwork {
    override suspend fun getImages(): Resource<List<Image>?> {
        return try {
            val result = imagesService.loadImages()
            Resource.Success(result.map { it.toPicture() })
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }
}