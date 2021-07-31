package com.rumosoft.feature_images.domain.usecases

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.RemoveImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
import javax.inject.Inject

class RemoveImageUseCaseImpl @Inject constructor(
    private val repository: ImagesRepository,
) : RemoveImageUseCase {
    override suspend fun invoke(image: Image): Resource<Unit> = repository.removeImage(image)
}
