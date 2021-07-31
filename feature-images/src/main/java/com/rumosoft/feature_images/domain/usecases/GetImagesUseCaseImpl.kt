package com.rumosoft.feature_images.domain.usecases

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
import javax.inject.Inject

class GetImagesUseCaseImpl @Inject constructor(
    private val repository: ImagesRepository,
) : GetImagesUseCase {
    override suspend fun invoke(): Resource<List<Image>?> = repository.getImages()
}
