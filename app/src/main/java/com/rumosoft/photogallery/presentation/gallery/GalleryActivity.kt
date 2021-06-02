package com.rumosoft.photogallery.presentation.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.rumosoft.photogallery.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class GalleryActivity : AppCompatActivity() {
    private lateinit var galleryFragment: GalleryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
        if (savedInstanceState == null) {
            galleryFragment = GalleryFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, galleryFragment)
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gallery_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_add_picture -> {
                Timber.d("Clicked add picture")
                galleryFragment.showImageChooser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}