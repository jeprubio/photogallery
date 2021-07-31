package com.rumosoft.feature_images.infrastructure.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.onClick(onClickListener: (View) -> Unit) {
    setOnClickListener(onClickListener)
}

fun View.hide() {
    visibility = GONE
}

fun View.show() {
    visibility = VISIBLE
}
