package com.shashavs.trackviewer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shashavs.trackviewer.R

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val darkColors = darkColorScheme(
//        primary = Color(0xFFADC6FF),
//        secondary = Color(0xFFBBC6E4),
//        tertiary = Color(0xFFE5B8E8),
//        error = Color(0xFFFFB4AB),
//        background = Color(0xFF1B1B1F),
//        surfaceVariant = Color(0xFF),
//        surface = Color(0xFF1B1B1F),
//    )

//    val lightColors = lightColorScheme(
//        primary = Color(0xFF005AC1),
//        secondary = Color(0xFF535E78),
//        tertiary = Color(0xFF76517B),
//        error = Color(0xFFBA1A1A),
//        background = Color(0xFFFEFBFF),
//        surface = Color(0xFFFEFBFF),
//        onSurface = Color(0xFF1B1B1F),
//        surfaceVariant = Color(0xFFE1E2EC),
//        onSurfaceVariant = Color(0xFF44474F),
//        inverseSurface = Color(0xFF303033),
//        outline = Color(0xFF74777F)
//    )

    val fonts = FontFamily(
        Font(R.font.roboto_regular, weight = FontWeight.Normal),
        Font(R.font.roboto_medium, weight = FontWeight.Medium)
    )

//    val typography = Typography(
//        displayLarge = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 57.sp,
//        ),
//        displayMedium = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 45.sp
//        ),
//        displaySmall = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 38.sp
//        ),
//        headlineLarge = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 32.sp
//        ),
//        headlineMedium = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 28.sp,
//        ),
//        headlineSmall = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 24.sp
//        ),
//        titleLarge = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 22.sp,
//            color = MaterialTheme.colorScheme.onSurface
//        ),
//        titleMedium = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Medium,
//            fontSize = 16.sp
//        ),
//        titleSmall = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Medium,
//            fontSize = 14.sp
//        ),
//        labelLarge = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Medium,
//            fontSize = 14.sp,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        ),
//        labelMedium = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Medium,
//            fontSize = 12.sp
//        ),
//        labelSmall = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Medium,
//            fontSize = 11.sp
//        ),
//        bodyLarge = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            color = MaterialTheme.colorScheme.inverseSurface
//        ),
//        bodyMedium = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 14.sp
//        ),
//        bodySmall = TextStyle(
//            fontFamily = fonts,
//            fontWeight = FontWeight.Normal,
//            fontSize = 12.sp
//        ),
//    )

    MaterialTheme(
//        colors = if (darkTheme) darkColors else lightColors,
//        typography = typography,
        content = content
    )
}