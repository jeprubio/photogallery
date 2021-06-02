package com.rumosoft.photogallery.domain.usecases

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.UpdateImageTitleUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.photogallery.infrastructure.Resource
import javax.inject.Inject

class UpdateImageTitleUseCaseImpl @Inject constructor(
        private val repository: ImagesRepository,
) : UpdateImageTitleUseCase {
    override suspend fun invoke(image: Image): Resource<Image> = repository.updateImageTitle(image)
}