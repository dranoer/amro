package com.amro.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LightTeal,
    onPrimary = DarkTeal,
    primaryContainer = Teal,
    onPrimaryContainer = Color.White,
    secondary = LightTeal,
    onSecondary = DarkTeal,
    background = DarkTeal,
    surface = DarkTeal,
    onBackground = TealMist,
    onSurface = TealMist,
    surfaceVariant = TealSlate,
    onSurfaceVariant = LightTeal
)

private val LightColorScheme = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    primaryContainer = LightTeal,
    onPrimaryContainer = DarkTeal,
    secondary = DarkTeal,
    onSecondary = Color.White,
    background = TealMist,
    surface = TealMist,
    onBackground = DarkTeal,
    onSurface = DarkTeal,
    surfaceVariant = Color.White,
    onSurfaceVariant = Teal
)

@Composable
fun AmroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
