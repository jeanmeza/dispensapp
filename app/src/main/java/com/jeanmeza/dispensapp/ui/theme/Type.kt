package com.jeanmeza.dispensapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.jeanmeza.dispensapp.R

val Lato = FontFamily(
    Font(R.font.lato_black, FontWeight.Black),
    Font(R.font.lato_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.lato_italic, style = FontStyle.Italic),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.lato_regular),
    Font(R.font.lato_thin, FontWeight.Thin),
    Font(R.font.lato_thin, FontWeight.Thin, FontStyle.Italic),
)

// Default Material 3 typography values
val baseline = Typography()

val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = Lato),
    displayMedium = baseline.displayMedium.copy(fontFamily = Lato),
    displaySmall = baseline.displaySmall.copy(fontFamily = Lato),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = Lato),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = Lato),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = Lato),
    titleLarge = baseline.titleLarge.copy(fontFamily = Lato),
    titleMedium = baseline.titleMedium.copy(fontFamily = Lato),
    titleSmall = baseline.titleSmall.copy(fontFamily = Lato),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = Lato),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = Lato),
    bodySmall = baseline.bodySmall.copy(fontFamily = Lato),
    labelLarge = baseline.labelLarge.copy(fontFamily = Lato),
    labelMedium = baseline.labelMedium.copy(fontFamily = Lato),
    labelSmall = baseline.labelSmall.copy(fontFamily = Lato),
)

