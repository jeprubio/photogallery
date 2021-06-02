package com.rumosoft.feature_images.domain.usecases.interfaces

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.infrastructure.Resource

interface AddImageUseCase {
    suspend operator fun invoke(image: Image): Resource<Long>
}