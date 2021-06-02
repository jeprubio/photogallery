package com.rumosoft.photogallery.presentation.listeners

import com.rumosoft.photogallery.domain.model.Image

class ImageClickListener(val clickListener: (itemId: Image) -> Unit) {
    operator fun invoke(itemId: Image) = clickListener(itemId)
}