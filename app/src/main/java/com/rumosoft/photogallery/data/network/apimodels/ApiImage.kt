package com.rumosoft.photogallery.data.network.apimodels

data class ApiImage(
        val albumId: Long?,
        val id: Long,
        val title: String?,
        val url: String?,
        val thumbnailUrl: String?,
)

