package com.rumosoft.feature_images.infrastructure.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.IOException
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * @see "http://developer.android.com/training/displaying-bitmaps/load-bitmap.html"
 */
private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        inSampleSize = if (width < height) {
            (width.toFloat() / reqHeight.toFloat()).roundToInt()
        } else {
            (height.toFloat() / reqWidth.toFloat()).roundToInt()
        }
    }
    return inSampleSize
}

fun decodeSampledBitmapFromUri(c: Context, uri: Uri, reqWidth: Int, reqHeight: Int): Bitmap? {

    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeStream(c.contentResolver.openInputStream(uri), null, options)

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeStream(c.contentResolver.openInputStream(uri), null, options)
}

@Throws(IOException::class)
fun getOrientation(uri: Uri): Int {
    val f = File(uri.path!!)
    if (!f.exists()) {
        return 0
    }

    val exif = ExifInterface(f.absolutePath)

    return when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_UNDEFINED -> 0
        else -> 0
    }
}

fun Bitmap.round(): Bitmap {
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val rect = Rect(0, 0, width, height)
    val paint = Paint().apply {
        isAntiAlias = true
        color = -0xbdbdbe
    }
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(
        (width / 2).toFloat(), (height / 2).toFloat(),
        (min(height, width) / 2).toFloat(),
        paint
    )
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    return output
}
