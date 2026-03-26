package com.nexfin.frontend.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// สร้างพาเลตต์สำหรับ Dark Theme โดยเฉพาะ
private val DarkAppColorScheme = darkColorScheme(
    primary = NexFinColors.NeonCyan,
    secondary = NexFinColors.NeonPurple,
    background = NexFinColors.DarkBackground,
    surface = NexFinColors.DarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = NexFinColors.TextPrimary,
    onSurface = NexFinColors.TextPrimary,
    error = NexFinColors.Danger
)

// อันนี้ทำเผื่อไว้เฉยๆ
private val LightAppColorScheme = lightColorScheme(
    primary = NexFinColors.Forest,
    secondary = NexFinColors.Seafoam,
    background = NexFinColors.Sand,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = NexFinColors.Danger
)

@Composable
fun NexFinTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkAppColorScheme
    } else {
        LightAppColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NexFinTypography, // <-- แก้ตรงนี้เป็น NexFinTypography แล้ว
        content = content
    )
}