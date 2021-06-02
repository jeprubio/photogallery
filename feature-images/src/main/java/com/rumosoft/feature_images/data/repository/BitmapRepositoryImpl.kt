package com.rumosoft.feature_images.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.BitmapRepository
import com.rumosoft.feature_images.infrastructure.extensions.decodeSampledBitmapFromUri
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject


class BitmapRepositoryImpl @Inject constructor(private val context: Context) : BitmapRepository {

    override fun storeImageFromContentUri(uri: Uri, width: Int?, height: Int?): String? {
        val bitmap = if (width != null && height != null)
            decodeSampledBitmapFromUri(context, uri, width, height)
        else {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        }
        return saveBitmap(bitmap)
    }

    private fun saveBitmap(resizedBitmap: Bitmap?): String? {
        return try {
            resizedBitmap?.let {
                val picName = "img_" + UUID.randomUUID().toString() + ".jpg"
                saveFile(context, it, picName)
                File(context.filesDir, picName).toString()
            }
        } catch (e: Throwable) {
            null
        }
    }

    private fun saveFile(context: Context, bitmap: Bitmap, picName: String) {
        val fos: FileOutputStream = context.openFileOutput(picName, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
    }
}