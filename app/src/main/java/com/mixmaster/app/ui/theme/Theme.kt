package com.mixmaster.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary             = GoldAccent,
    onPrimary           = TextOnGold,
    primaryContainer    = Color(0xFF2A2010),
    onPrimaryContainer  = GoldLight,
    secondary           = CopperAccent,
    onSecondary         = TextOnGold,
    secondaryContainer  = Color(0xFF1E1408),
    onSecondaryContainer = GoldLight,
    background          = BgPrimary,
    onBackground        = TextPrimary,
    surface             = BgSurface,
    onSurface           = TextPrimary,
    surfaceVariant      = BgSecondary,
    onSurfaceVariant    = TextSecondary,
    error               = ErrorRed,
    onError             = TextPrimary,
    outline             = DividerGold,
    outlineVariant      = GlassBorder
)

@Composable
fun MixMasterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = Typography,
        content     = content
    )
}
