package com.rumosoft.photogallery.infrastructure.di

import com.rumosoft.photogallery.BuildConfig
import com.rumosoft.feature_images.data.network.ImagesNetwork
import com.rumosoft.feature_images.data.network.ImagesNetworkImpl
import com.rumosoft.feature_images.data.network.ImagesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient().newBuilder().build()

    @Provides
    @Singleton
    fun provideService(
            okHttpClient: OkHttpClient,
            baseUrl: String,
    ): ImagesService =
            Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(ImagesService::class.java)

    @Provides
    @Singleton
    fun providesNetwork(
            imagesService: ImagesService,
    ): ImagesNetwork = ImagesNetworkImpl(imagesService)
}