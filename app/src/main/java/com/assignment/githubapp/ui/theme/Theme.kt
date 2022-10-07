package com.assignment.githubapp.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    background = Color.Black,
    surface = Color.Black,
    error = Color(0xFFEe13f0d),
    secondary = Color(0xFF87cecc),
    secondaryVariant = White
)

private val PrimaryColorPalette = lightColors(
    primary = Color.Black,
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFEe13f0d),
    secondary = Color(0xFF87cecc),
    secondaryVariant = Black
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GitHubAppTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if(isDarkTheme) DarkColorPalette else PrimaryColorPalette,
        typography = Typography,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalOverScrollConfiguration provides null,
            content = content
        )
    }
}