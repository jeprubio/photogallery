package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.data.network.apimodels.ApiId
import com.rumosoft.photogallery.data.network.apimodels.ApiImage
import com.rumosoft.photogallery.data.network.apimodels.ApiTitle
import retrofit2.http.*

interface ImagesService {
    @GET("photos")
    suspend fun loadImages(): List<ApiImage>

    @POST("photos")
    suspend fun addImage(
            @Body image: ApiImage,
    ): ApiId

    @PUT("photos/${PHOTO_ID}")
    suspend fun editImage(
            @Path(PHOTO_ID) photoId: Long,
            @Body image: ApiImage,
    ): ApiId

    @PATCH("photos/${PHOTO_ID}")
    suspend fun updateTitleImage(
            @Path(PHOTO_ID) photoId: Long,
            @Body title: ApiTitle,
    ): ApiImage

    @DELETE("photos/${PHOTO_ID}")
    suspend fun removeImage(
            @Path(PHOTO_ID) photoId: Long,
    )

    companion object {
        private const val PHOTO_ID = "photoId"
    }
}