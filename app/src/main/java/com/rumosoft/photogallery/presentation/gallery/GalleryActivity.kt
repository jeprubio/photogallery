package com.rumosoft.photogallery.presentation.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rumosoft.photogallery.R

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, GalleryFragment.newInstance())
                    .commitNow()
        }
    }
}