package com.ulises.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val NunitoFamily = FontFamily(
    Font(R.font.nunito, FontWeight.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold),
)
val PijamadaTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        color = TextPrimary,
    ),
    headlineMedium = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        color = TextPrimary,
    ),
    titleLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = TextPrimary,
    ),
    titleMedium = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = TextPrimary,
    ),
    bodyLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        color = TextSecondary,
    ),
    bodyMedium = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        color = TextMuted,
    ),
    labelLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        letterSpacing = 0.4.sp,
        color = TextPrimary,
    ),
    labelMedium = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp,
        color = TextSecondary,
    ),
)