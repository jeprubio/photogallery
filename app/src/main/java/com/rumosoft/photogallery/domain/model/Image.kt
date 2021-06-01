package com.rumosoft.photogallery.domain.model

data class Image(
        val id: Long,
        val title: String,
        val image: String,
        val thumbnail: String,
)
