package com.rumosoft.feature_images.infrastructure.di

import android.content.Context
import com.rumosoft.feature_images.data.database.ImageDao
import com.rumosoft.feature_images.data.network.ImagesNetwork
import com.rumosoft.feature_images.data.repository.BitmapRepositoryImpl
import com.rumosoft.feature_images.data.repository.ImagesRepositoryImpl
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.BitmapRepository
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(
        imagesNetwork: ImagesNetwork,
        imageDao: ImageDao,
    ): ImagesRepository = ImagesRepositoryImpl(imagesNetwork, imageDao)

    @Provides
    @Singleton
    fun provideBitmapRepository(
        @ApplicationContext appContext: Context
    ): BitmapRepository = BitmapRepositoryImpl(appContext)
}
