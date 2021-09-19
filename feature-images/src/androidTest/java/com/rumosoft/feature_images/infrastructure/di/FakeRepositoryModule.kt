package com.rumosoft.feature_images.infrastructure.di

import com.rumosoft.feature_images.domain.usecases.interfaces.repository.BitmapRepository
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.testUtils.BitmapRepositoryMock
import com.rumosoft.feature_images.testUtils.ImagesRepositoryMock
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object FakeRepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(): ImagesRepository = ImagesRepositoryMock()

    @Provides
    @Singleton
    fun provideBitmapRepository(): BitmapRepository = BitmapRepositoryMock()
}
