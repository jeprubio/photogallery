package com.rumosoft.photogallery.domain.usecases.interfaces.repository

import android.net.Uri

interface BitmapRepository {
    fun storeImageFromContentUri(uri: Uri, width: Int?, height: Int?): String?
}