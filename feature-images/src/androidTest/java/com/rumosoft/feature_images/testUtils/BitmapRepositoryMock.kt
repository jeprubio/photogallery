package com.rumosoft.feature_images.testUtils

import android.net.Uri
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.BitmapRepository

class BitmapRepositoryMock : BitmapRepository {
    override fun storeImageFromContentUri(uri: Uri, width: Int?, height: Int?): String {
        return ""
    }
}
