package com.rumosoft.photogallery.domain.usecases

import com.rumosoft.photogallery.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource
import javax.inject.Inject

class GetImagesUseCaseImpl @Inject constructor(
        private val repository: ImagesRepository,
) : GetImagesUseCase {
    override suspend fun invoke(): Resource<List<Image>?> = repository.getImages()
}