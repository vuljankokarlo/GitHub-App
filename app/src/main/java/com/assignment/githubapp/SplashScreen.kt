package com.assignment.githubapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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
        Text(
            "Spash screen",
            style = MaterialTheme.typography.OpenSansBold_16_24,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    Log.i(GitHubApp.TAG, "Hello from splash screen")
                }

        )
    }
}