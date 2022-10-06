package com.assignment.githubapp.ui.theme

import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)


val String.color
    get() = Color(android.graphics.Color.parseColor(this))
