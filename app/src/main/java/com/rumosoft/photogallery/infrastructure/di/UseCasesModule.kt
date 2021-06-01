package com.rumosoft.photogallery.infrastructure.di

import com.rumosoft.photogallery.domain.usecases.GetImagesUseCaseImpl
import com.rumosoft.photogallery.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    fun provideGetImagesUseCase(
        repository: ImagesRepository,
    ): GetImagesUseCase = GetImagesUseCaseImpl(repository)
}