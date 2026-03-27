package com.nexfin.frontend.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun rememberImageSaver(): ImageSaver {
    return remember { DesktopImageSaver() }
}

private class DesktopImageSaver : ImageSaver {
    override fun saveToGallery(bitmap: ImageBitmap, fileName: String): Boolean {
        return false
    }
}
