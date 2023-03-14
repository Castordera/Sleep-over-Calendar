package com.example.sleepschedule.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xffcfbcff),
    secondary = Color(0xffcbc2db),
    surface = Color(0xFF49454F),
    background = Color(0xff1c1b1e)

)

private val LightColorPalette = lightColors(
    primary = Color(0xff6750a4),
    secondary = Color(0xff625b71),
    surface = Color(0xFFE7E0EC),
    background = Color(0xfffffbff)
//    background = Color.LightGray,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SleepScheduleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}