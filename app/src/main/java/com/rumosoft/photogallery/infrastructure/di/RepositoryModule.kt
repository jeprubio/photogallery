package com.rumosoft.photogallery.infrastructure.di

import com.rumosoft.photogallery.data.network.ImagesNetwork
import com.rumosoft.photogallery.data.repository.ImagesRepositoryImpl
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(
            imagesNetwork: ImagesNetwork
    ): ImagesRepository = ImagesRepositoryImpl(imagesNetwork)
}