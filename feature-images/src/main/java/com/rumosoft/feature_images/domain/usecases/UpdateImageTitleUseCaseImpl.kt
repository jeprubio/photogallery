package com.rumosoft.feature_images.domain.usecases

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.UpdateImageTitleUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
import javax.inject.Inject

class UpdateImageTitleUseCaseImpl @Inject constructor(
    private val repository: ImagesRepository,
) : UpdateImageTitleUseCase {
    override suspend fun invoke(image: Image): Resource<Image> = repository.updateImageTitle(image)
}
