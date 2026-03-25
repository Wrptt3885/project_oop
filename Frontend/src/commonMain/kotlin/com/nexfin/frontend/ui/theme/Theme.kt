package com.nexfin.frontend.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = NexFinColors.Forest,
    secondary = NexFinColors.Gold,
    tertiary = NexFinColors.Seafoam,
    background = NexFinColors.Sand,
    surface = NexFinColors.Card,
    onPrimary = NexFinColors.Card,
    onBackground = NexFinColors.Ink,
    onSurface = NexFinColors.Ink,
    error = NexFinColors.Danger
)

private val DarkColors = darkColorScheme(
    primary = NexFinColors.Seafoam,
    secondary = NexFinColors.Gold,
    background = NexFinColors.Ink,
    surface = Color(0xFF203029),
    onPrimary = NexFinColors.Ink,
    onBackground = NexFinColors.Card,
    onSurface = NexFinColors.Card,
    error = NexFinColors.Danger
)

@Composable
fun NexFinTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = NexFinTypography
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.18f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f)
                        )
                    )
                )
        ) {
            content()
        }
    }
}
