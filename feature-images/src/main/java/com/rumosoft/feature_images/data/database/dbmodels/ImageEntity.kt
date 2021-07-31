package com.rumosoft.feature_images.data.database.dbmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey val id: Long,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("image") val image: String,
    @field:SerializedName("thumbnail") val thumbnail: String,
)
