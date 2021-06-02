package com.rumosoft.photogallery.infrastructure.di

import android.content.Context
import androidx.room.Room
import com.rumosoft.feature_images.data.database.ImagesDatabase
import com.rumosoft.feature_images.data.database.ImagesDbName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideImageDao(prodDatabase: ImagesDatabase) = prodDatabase.imageDao()

    @Provides
    @Singleton
    fun provideProductsDatabase(
        @ApplicationContext appContext: Context
    ): ImagesDatabase = Room.databaseBuilder(
        appContext,
        ImagesDatabase::class.java,
        ImagesDbName
    ).build()
}