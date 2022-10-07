package com.assignment.githubapp.ui.theme

import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFCCCCCC)
val DarkGray = Color(0xFF444444)
val Turquoise = Color(0xFF047384)


val String.color
    get() = Color(android.graphics.Color.parseColor(this))
