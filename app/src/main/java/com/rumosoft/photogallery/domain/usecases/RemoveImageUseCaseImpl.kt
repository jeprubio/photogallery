package com.rumosoft.photogallery.domain.usecases

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.RemoveImageUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.photogallery.infrastructure.Resource
import javax.inject.Inject

class RemoveImageUseCaseImpl @Inject constructor(
        private val repository: ImagesRepository,
) : RemoveImageUseCase {
    override suspend fun invoke(image: Image): Resource<Unit> = repository.removeImage(image)
}