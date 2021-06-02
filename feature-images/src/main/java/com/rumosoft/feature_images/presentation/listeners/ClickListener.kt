package com.rumosoft.feature_images.presentation.listeners

class ClickListener(val clickListener: () -> Unit) {
    operator fun invoke() = clickListener()
}