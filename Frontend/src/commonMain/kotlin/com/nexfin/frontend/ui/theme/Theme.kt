// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/theme/Theme.kt
package com.nexfin.frontend.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkAppColorScheme = darkColorScheme(
    primary = NexFinColors.PrimaryAccent,
    secondary = NexFinColors.SecondaryAccent,
    background = NexFinColors.DarkBackground,
    surface = NexFinColors.DarkSurface,
    onPrimary = Color.White,       // ตัวหนังสือบนปุ่มหลักเป็นสีขาว
    onSecondary = Color.White,     // ตัวหนังสือบนปุ่มรองเป็นสีขาว
    onBackground = NexFinColors.TextPrimary, // ตัวหนังสือบนพื้นหลังเป็นสีขาว
    onSurface = NexFinColors.TextPrimary,    // ตัวหนังสือบนการ์ดเป็นสีขาว
    error = NexFinColors.Danger
)

@Composable
fun NexFinTheme(
    darkTheme: Boolean = true, // บังคับ Dark Mode ให้หล่อๆ ไปเลย
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkAppColorScheme,
        typography = NexFinTypography, 
        content = content
    )
}