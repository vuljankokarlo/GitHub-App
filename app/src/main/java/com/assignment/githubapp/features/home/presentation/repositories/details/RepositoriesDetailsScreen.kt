package com.assignment.githubapp.features.home.presentation.repositories.details

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
import com.assignment.githubapp.GitHubApp
import com.assignment.githubapp.common.view.navigation.Navigator
import com.assignment.githubapp.ui.theme.OpenSansBold_16_24

@Composable
fun RepositoriesDetailsScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            "Repositories Details screen",
            style = MaterialTheme.typography.OpenSansBold_16_24,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    navController.navigate(
                        Navigator.Home.RepositoriesMain()
                    )
                }

        )
    }
}