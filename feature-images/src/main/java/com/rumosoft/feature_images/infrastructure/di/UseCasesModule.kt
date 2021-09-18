package com.rumosoft.feature_images.infrastructure.di

import com.rumosoft.feature_images.domain.usecases.AddImageUseCaseImpl
import com.rumosoft.feature_images.domain.usecases.EditImageUseCaseImpl
import com.rumosoft.feature_images.domain.usecases.GetImagesUseCaseImpl
import com.rumosoft.feature_images.domain.usecases.RemoveImageUseCaseImpl
import com.rumosoft.feature_images.domain.usecases.StoreImageFromContentUseCaseImpl
import com.rumosoft.feature_images.domain.usecases.UpdateImageTitleUseCaseImpl
import com.rumosoft.feature_images.domain.usecases.interfaces.AddImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.EditImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.RemoveImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.StoreImageFromContentUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.UpdateImageTitleUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.BitmapRepository
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
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
