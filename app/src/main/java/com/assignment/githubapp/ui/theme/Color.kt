package com.assignment.githubapp.ui.theme

import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val Turqoise = Color(0xFF047384)


val String.color
    get() = Color(android.graphics.Color.parseColor(this))
