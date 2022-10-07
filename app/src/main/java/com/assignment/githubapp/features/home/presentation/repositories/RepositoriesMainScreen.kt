package com.assignment.githubapp.features.home.presentation.repositories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.assignment.githubapp.common.view.components.InputField
import com.assignment.githubapp.common.view.components.TitleText
import com.assignment.githubapp.features.home.presentation.repositories.components.RepositoryItem

@Composable
fun RepositoriesMainScreen(
    navController: NavController,
    viewModel: RepositoriesViewModel = hiltViewModel()
) {
//    Debounce compose hack, do not try at home because:
//    "This function should not be used to (re-)launch ongoing tasks in response to callback events by way of storing callback data in MutableState passed to key1."
//    LaunchedEffect(key1 = viewModel.viewState.value.repositoryNameQuery) {
//        delay(500)
//        viewModel.onQueryFieldValueChange()
//    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initValues()
    }

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 60.dp)
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TitleText(
                text = "GitHub Repositories".uppercase(),
                modifier = Modifier
                    .padding(
                        top = viewModel.viewState.value.statusBarHeight.dp + 10.dp,
                        bottom = 20.dp
                    )
            )
        }

        item {
            InputField(
                placeholder = "GitHub repository name".uppercase(),
                text = viewModel.viewState.value.repositoryNameQuery,
                modifier = Modifier
                    .padding(bottom = 20.dp),
                errorMessage = viewModel.viewState.value.repositoryNameQueryError
            ) { newValue ->
                viewModel.onQueryFieldValueChange(newValue)
            }
        }

        //TODO add filters

        viewModel.viewState.value.githubRepositoriesData?.items?.let { repositoryList ->
            items(repositoryList) { repository ->
                RepositoryItem(
                    repository = repository,
                    onAvatarClick = {
                        //TODO
                    },
                    onDetailsClick = {
                        //TODO
                    }
                )
            }
        }
    }
}