package com.nexfin.frontend.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberImageSaver(): ImageSaver {
    val context = LocalContext.current
    return remember { AndroidImageSaver(context) }
}

class AndroidImageSaver(private val context: Context) : ImageSaver {
    override fun saveToGallery(bitmap: ImageBitmap, fileName: String): Boolean {
        return try {
            val androidBitmap = bitmap.asAndroidBitmap()
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.png")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/NexFin")
            }

            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                ?: return false

            resolver.openOutputStream(uri)?.use { outputStream ->
                androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            } ?: return false

            true
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }
}
