package com.assignment.githubapp.features.home.presentation.repositories.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.assignment.githubapp.common.view.navigation.Navigator
import com.assignment.githubapp.ui.theme.OpenSansBold_16_24

@Composable
fun RepositoryDetailsScreen(
    navController: NavController,
    repositoryId: Int,
    viewModel: RepositoryDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.initViewModel(repositoryId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (viewModel.viewState.value.isLoading)
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        else {
            viewModel.viewState.value.repository?.let {
                Text(
                    it.repositoryName,
                    style = MaterialTheme.typography.OpenSansBold_16_24,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}