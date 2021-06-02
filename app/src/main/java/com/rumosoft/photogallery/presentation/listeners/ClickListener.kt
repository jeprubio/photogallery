package com.rumosoft.photogallery.presentation.listeners

class ClickListener(val clickListener: () -> Unit) {
    operator fun invoke() = clickListener()
}