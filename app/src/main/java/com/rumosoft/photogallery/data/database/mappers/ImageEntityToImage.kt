package com.rumosoft.photogallery.data.database.mappers

import com.rumosoft.photogallery.data.database.dbmodels.ImageEntity
import com.rumosoft.photogallery.domain.model.Image

fun ImageEntity.toImage(): Image = Image(
        id = id,
        title = title,
        image = image,
        thumbnail = thumbnail,
)