package com.rumosoft.feature_images.domain.usecases

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.EditImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
import javax.inject.Inject

class EditImageUseCaseImpl @Inject constructor(
    private val repository: ImagesRepository,
) : EditImageUseCase {
    override suspend fun invoke(image: Image): Resource<Long> = repository.editImage(image)
}
