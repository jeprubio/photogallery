package com.rumosoft.photogallery.domain.usecases.interfaces

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource

interface GetImagesUseCase {
    suspend operator fun invoke(): Resource<List<Image>?>
}