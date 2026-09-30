package com.example.digifyerp.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = DigifyPrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = DigifyBlueLight,
    onPrimaryContainer = DigifyBlueDark,
    secondary = DigifyRed,
    onSecondary = Color.White,
    secondaryContainer = DigifyRedLight,
    onSecondaryContainer = DigifyRedHover,
    background = DigifyBackground,
    onBackground = TextPrimary,
    surface = DigifyCardBackground,
    onSurface = TextPrimary,
    surfaceVariant = DigifyCardBorder,
    onSurfaceVariant = TextSecondary
)

@Composable
fun DigifyERPTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
