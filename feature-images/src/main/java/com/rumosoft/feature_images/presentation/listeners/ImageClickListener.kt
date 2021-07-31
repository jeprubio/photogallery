package com.rumosoft.feature_images.presentation.listeners

import com.rumosoft.feature_images.domain.model.Image

class ImageClickListener(val clickListener: (itemId: Image) -> Unit) {
    operator fun invoke(itemId: Image) = clickListener(itemId)
}
