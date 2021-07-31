package com.rumosoft.feature_images.data.repository

import com.rumosoft.feature_images.data.database.ImageDao
import com.rumosoft.feature_images.data.database.mappers.toImage
import com.rumosoft.feature_images.data.network.ImagesNetwork
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesNetwork: ImagesNetwork,
    private val imageDao: ImageDao,
) : ImagesRepository {
    override suspend fun getImages(): Resource<List<Image>?> {
        return when (val networkResponse = performNetworkCall()) {
            is Resource.Success -> networkResponse
            is Resource.Error -> {
                performDatabaseSearch(networkResponse)
            }
        }
    }

    override suspend fun addImage(image: Image): Resource<Long> = imagesNetwork.addImage(image)

    override suspend fun editImage(image: Image): Resource<Long> = imagesNetwork.editImage(image)

    override suspend fun updateImageTitle(image: Image): Resource<Image> =
        imagesNetwork.updateImageTitle(image)

    override suspend fun removeImage(image: Image): Resource<Unit> =
        imagesNetwork.removeImage(image)

    private suspend fun performNetworkCall(): Resource<List<Image>?> {
        val networkData = imagesNetwork.getImages()
        if (networkData is Resource.Success) {
            networkData.data?.let { imagesList ->
                imageDao.insertAll(imagesList.map { it.toImage() })
            }
        }
        return networkData
    }

    private suspend fun performDatabaseSearch(networkException: Resource.Error) = try {
        Resource.Success(imageDao.getImages().map { it.toImage() })
    } catch (dbException: Exception) {
        networkException
    }
}
