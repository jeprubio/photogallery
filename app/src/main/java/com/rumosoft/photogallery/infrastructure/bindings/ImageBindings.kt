package com.rumosoft.photogallery.infrastructure.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.rumosoft.photogallery.R

/**
 * Binding to load a image url in an ImageView and perform a CircleCrop transformation
 */
@BindingAdapter("circularImageUrl")
fun loadCircularImage(imageView: ImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher)
        transformations(CircleCropTransformation())
    }
}

/**
 * Binding to load a image url in an ImageView
 */
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher)
    }
}