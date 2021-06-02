package com.rumosoft.feature_images.domain.usecases.interfaces

import android.net.Uri

interface StoreImageFromContentUseCase {
    operator fun invoke(uri: Uri, width: Int?, height: Int?): String?
}