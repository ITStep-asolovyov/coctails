package com.mixmaster.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldAccent,
    onPrimary = BackgroundDeep,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = PurpleLight,
    secondary = PurplePrimary,
    onSecondary = TextPrimary,
    secondaryContainer = PurpleDark,
    onSecondaryContainer = PurpleLight,
    background = BackgroundDeep,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceElevated,
    onSurfaceVariant = TextSecondary,
    error = Color(0xFFCF6679),
    onError = TextPrimary
)

@Composable
fun MixMasterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
