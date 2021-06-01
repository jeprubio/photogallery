package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.data.network.apimodels.ApiImage
import retrofit2.http.GET

interface ImagesService {
    @GET("photos")
    suspend fun loadImages(): List<ApiImage>
}