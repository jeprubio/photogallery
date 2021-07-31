package com.rumosoft.feature_images.data.database.mappers

import com.rumosoft.feature_images.data.database.dbmodels.ImageEntity
import com.rumosoft.feature_images.domain.model.Image

fun Image.toImage(): ImageEntity = ImageEntity(
    id = id,
    title = title,
    image = image,
    thumbnail = thumbnail,
)
