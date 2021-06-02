package com.rumosoft.photogallery.domain.usecases.interfaces

import android.net.Uri

interface StoreImageFromContentUseCase {
    operator fun invoke(uri: Uri, width: Int?, height: Int?): String?
}