package com.nexfin.frontend.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

interface ImageSaver {
    fun saveToGallery(bitmap: ImageBitmap, fileName: String): Boolean
}

@Composable
expect fun rememberImageSaver(): ImageSaver