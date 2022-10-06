package com.assignment.githubapp.common.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*

@Composable
fun LottieLoader(
    lottieJson: Int,
    modifier: Modifier = Modifier,
    iterations: Int = 1,
    onFinish: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieJson))
    val progress by animateLottieCompositionAsState(composition, iterations = iterations)

    LottieAnimation(
        composition,
        progress,
        modifier = modifier
    )

    if (progress == 1f)
        LaunchedEffect(key1 = Unit) {
            onFinish()
        }
}