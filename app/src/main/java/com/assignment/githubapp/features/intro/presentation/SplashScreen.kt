package com.assignment.githubapp.features.intro.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.assignment.githubapp.GitHubApp
import com.assignment.githubapp.R
import com.assignment.githubapp.common.view.components.LottieLoader
import com.assignment.githubapp.common.view.navigation.Navigator
import com.assignment.githubapp.ui.theme.OpenSansBold_16_24

@Composable
fun SplashScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.gradient_dark_blue),
                contentDescription = "splash screen image",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.FillBounds
            )
            LottieLoader(lottieJson = R.raw.splash_screen_anim, modifier = Modifier.align(Alignment.Center)) {
                navController.navigate(Navigator.Home.RepositoriesMain())
            }
        }
    }
}