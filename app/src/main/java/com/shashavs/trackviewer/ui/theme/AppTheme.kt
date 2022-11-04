package com.shashavs.trackviewer.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.shashavs.trackviewer.R

@Composable
fun AppTheme(
    darkTheme: Boolean = true,
//    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val appDarkColors = darkColors(
        primary = Color(0xFFADC6FF),
        secondary = Color(0xFFBBC6E4),
        error = Color(0xFFFFB4AB),
        background = Color(0xFF1B1B1F),
        surface = Color(0xFF1B1B1F)
    )

    val appLightColors = lightColors(
        primary = Color(0xFF005AC1),
        secondary = Color(0xFF535E78),
        error = Color(0xFFBA1A1A),
        background = Color(0xFFFEFBFF),
        surface = Color(0xFFFEFBFF),
    )

    val fonts = FontFamily(
        Font(R.font.roboto_regular, weight = FontWeight.Normal),
        Font(R.font.roboto_medium, weight = FontWeight.Medium)
    )

    val typography = Typography(
        defaultFontFamily = fonts
    )

    MaterialTheme(
        colors = if (darkTheme) appDarkColors else appLightColors,
        typography = typography,
        content = content
    )
}