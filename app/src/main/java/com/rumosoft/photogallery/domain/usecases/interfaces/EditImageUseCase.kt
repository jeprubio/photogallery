package com.rumosoft.photogallery.domain.usecases.interfaces

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource

interface EditImageUseCase {
    suspend operator fun invoke(image: Image): Resource<Long>
}