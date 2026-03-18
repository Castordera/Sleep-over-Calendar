package com.ulises.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

internal val DarkColorScheme = darkColorScheme(
    primary = LavenderGlow,
    onPrimary = Color(0xFF1A0D4A),
    primaryContainer = Color(0xFF3D2E8A),
    onPrimaryContainer = LavenderLight,
    secondary = RosePetal,
    onSecondary = Color(0xFF4A1430),
    secondaryContainer = Color(0xFF6B2B4E),
    onSecondaryContainer = Color(0xFFFFD6E8),
    tertiary = StarGold,
    onTertiary = Color(0xFF3B2000),
    background = NightDeep,
    onBackground = TextPrimary,
    surface = NightSurface,
    onSurface = TextPrimary,
    surfaceVariant = NightElevated,
    onSurfaceVariant = TextSecondary,
    outline = NightRim,
    error = DangerRed,
    onError = Color(0xFF410002),
)