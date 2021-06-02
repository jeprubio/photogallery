package com.rumosoft.feature_images.domain.usecases.interfaces

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.infrastructure.Resource

interface GetImagesUseCase {
    suspend operator fun invoke(): Resource<List<Image>?>
}