package com.rumosoft.photogallery.domain.usecases.interfaces.repository

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource

interface ImagesRepository {
    suspend fun getImages(): Resource<List<Image>?>
}