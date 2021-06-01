package com.rumosoft.photogallery.infrastructure

import android.app.Application
import com.rumosoft.photogallery.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Application
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // To initialize timber lib only in debug
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
