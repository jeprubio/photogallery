package com.rumosoft.photogallery.infrastructure.di

import android.content.Context
import com.rumosoft.photogallery.data.database.ImageDao
import com.rumosoft.photogallery.data.database.ImagesDatabase_Impl
import com.rumosoft.photogallery.data.network.ImagesNetwork
import com.rumosoft.photogallery.data.repository.BitmapRepositoryImpl
import com.rumosoft.photogallery.data.repository.ImagesRepositoryImpl
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.BitmapRepository
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
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