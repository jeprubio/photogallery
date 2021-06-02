package com.rumosoft.photogallery.domain.usecases

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.EditImageUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.photogallery.infrastructure.Resource
import javax.inject.Inject

class EditImageUseCaseImpl @Inject constructor(
        private val repository: ImagesRepository,
) : EditImageUseCase {
    override suspend fun invoke(image: Image): Resource<Long> = repository.editImage(image)
}