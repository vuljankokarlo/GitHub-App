package com.assignment.githubapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun StatusBarWrapper(
    statusBarHeight: Float,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        content()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(statusBarHeight.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.75f),
                            Color.Transparent
                        )
                    )
                )
        ) {}
    }
}