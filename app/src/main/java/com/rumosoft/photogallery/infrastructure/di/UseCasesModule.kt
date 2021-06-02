package com.rumosoft.photogallery.infrastructure.di

import com.rumosoft.photogallery.domain.usecases.*
import com.rumosoft.photogallery.domain.usecases.interfaces.*
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.BitmapRepository
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

    @Provides
    fun provideAddImageUseCase(
            repository: ImagesRepository,
    ): AddImageUseCase = AddImageUseCaseImpl(repository)

    @Provides
    fun provideEditImageUseCase(
            repository: ImagesRepository,
    ): EditImageUseCase = EditImageUseCaseImpl(repository)

    @Provides
    fun provideUpdateImageTitleUseCase(
            repository: ImagesRepository,
    ): UpdateImageTitleUseCase = UpdateImageTitleUseCaseImpl(repository)

    @Provides
    fun provideRemoveImageUseCase(
            repository: ImagesRepository,
    ): RemoveImageUseCase = RemoveImageUseCaseImpl(repository)

    @Provides
    fun provideStoreImageFromContentUseCase(
            repository: BitmapRepository,
    ): StoreImageFromContentUseCase = StoreImageFromContentUseCaseImpl(repository)
}