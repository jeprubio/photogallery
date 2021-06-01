package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource

interface ImagesNetwork {
    suspend fun getImages(): Resource<List<Image>?>
}