package com.rumosoft.feature_images.infrastructure.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.clear
import coil.load
import com.rumosoft.feature_images.R
import java.io.File

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    url?.takeIf { it.isNotBlank() }?.let {
        loadImageWithCoil(url, imageView)
    } ?: run {
        imageView.clear()
        imageView.setImageResource(R.mipmap.ic_launcher)
    }
}

private fun loadImageWithCoil(url: String, imageView: ImageView) =
    if (url.startsWith("http")) {
        loadRemoteImage(imageView, url)
    } else {
        loadLocalImage(imageView, url)
    }

private fun loadRemoteImage(imageView: ImageView, url: String) =
    imageView.load(url) {
        crossfade(false)
        placeholder(R.mipmap.ic_launcher)
        fallback(R.mipmap.ic_launcher)
        error(R.mipmap.ic_launcher)
    }

private fun loadLocalImage(imageView: ImageView, url: String) =
    imageView.load(File(url)) {
        crossfade(false)
        placeholder(R.mipmap.ic_launcher)
        fallback(R.mipmap.ic_launcher)
        error(R.mipmap.ic_launcher)
    }
