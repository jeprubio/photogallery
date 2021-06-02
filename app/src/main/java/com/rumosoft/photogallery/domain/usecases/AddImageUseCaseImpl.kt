package com.rumosoft.photogallery.domain.usecases

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.AddImageUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.photogallery.infrastructure.Resource
import javax.inject.Inject

class AddImageUseCaseImpl @Inject constructor(
        private val repository: ImagesRepository,
) : AddImageUseCase {
    override suspend fun invoke(image: Image): Resource<Long> = repository.addImage(image)
}