package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.data.network.apimodels.ApiTitle
import com.rumosoft.photogallery.data.network.mappers.toApiImage
import com.rumosoft.photogallery.data.network.mappers.toImage
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
            Resource.Success(result.map { it.toImage() })
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }

    override suspend fun addImage(image: Image): Resource<Long> {
        return try {
            val result = imagesService.addImage(image.toApiImage())
            Resource.Success(result.id)
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }

    override suspend fun editImage(image: Image): Resource<Long> {
        return try {
            val result = imagesService.editImage(image.id, image.toApiImage())
            Resource.Success(result.id)
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }

    override suspend fun updateTitleImage(image: Image): Resource<Image> {
        return try {
            val title = ApiTitle(image.title)
            val result = imagesService.updateTitleImage(image.id, title)
            Resource.Success(result.toImage())
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }

    override suspend fun removeImage(image: Image): Resource<Unit> {
        return try {
            imagesService.removeImage(image.id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }
}