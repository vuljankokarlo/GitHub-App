package com.assignment.githubapp.features.home.presentation.repositories

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.assignment.githubapp.GitHubApp
import com.assignment.githubapp.R
import com.assignment.githubapp.common.util.isScrolledToTheEnd
import com.assignment.githubapp.common.view.components.InputField
import com.assignment.githubapp.common.view.components.TitleText
import com.assignment.githubapp.common.view.navigation.Navigator
import com.assignment.githubapp.features.home.domain.model.SortType
import com.assignment.githubapp.features.home.presentation.repositories.components.RepositoryItem
import com.assignment.githubapp.ui.theme.OpenSansRegular_12_16
import com.assignment.githubapp.ui.theme.Turquoise
import io.realm.BuildConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current

    val customTabsIntent = remember {
        mutableStateOf(CustomTabsIntent.Builder().build())
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initValues()
        Log.i(GitHubApp.TAG, com.assignment.githubapp.BuildConfig.FLAVOR)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.oneShotEvents
            .onEach {
                when (it) {
                    is RepositoriesViewModel.OneShotEvent.NavigateToResults -> {
                        navController.navigate(
                            route = "${Navigator.Home.RepositoryDetails()}${it.id}"
                        ) {
                            launchSingleTop = true
                        }
                    }
                }
            }
            .collect()
    }

    val isLastItemFullyVisible by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
    }

    val isScrollToTopShowing by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0 > 5
        }
    }

    LaunchedEffect(key1 = isLastItemFullyVisible) {
        if (lazyListState.isScrolledToTheEnd() && viewModel.viewState.value.gitHubRepositoriesData != null) {
            viewModel.onScrollEnd()
        }
    }

    Box(
        modifier = Modifier
            .padding(bottom = 60.dp)
            .fillMaxSize()
    ) {
        if (viewModel.viewState.value.repositoryList.isNullOrEmpty())
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(horizontal = 20.dp)
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

            item {
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Sort by:",
                        style = MaterialTheme.typography.OpenSansRegular_12_16,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SortType.values().forEach {
                        Icon(
                            painterResource(
                                it.icon
                            ),
                            tint = if (viewModel.viewState.value.sort == it) Turquoise else MaterialTheme.colors.primary,
                            contentDescription = "sort type icon",
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .border(1.dp, MaterialTheme.colors.primary)
                                .padding(10.dp)
                                .size(24.dp)
                                .clickable {
                                    viewModel.onSortTypeChange(it)
                                }
                        )
                    }
                    Text(
                        text = viewModel.viewState.value.order.name,
                        style = MaterialTheme.typography.OpenSansRegular_12_16,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .clickable {
                                viewModel.onOrderTypeChange()
                            }
                    )
                }
            }

            viewModel.viewState.value.repositoryList?.let { repositoryList ->
                items(repositoryList) { repository ->
                    RepositoryItem(
                        repository = repository,
                        onAvatarClick = {
                            customTabsIntent.value.launchUrl(
                                context,
                                Uri.parse(it.profileUrl)
                            )
                        },
                        onDetailsClick = {
                            viewModel.navigateToDetails(repository.id)
                        }
                    )
                }
            }
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = isScrollToTopShowing,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp),
            enter = fadeIn(
                animationSpec = tween(500)
            ),
            exit = fadeOut(
                animationSpec = tween(500)
            )
        ) {
            Button(
                onClick = {
                    coroutineScope.launch { lazyListState.animateScrollToItem(0) }
                },
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(10.dp, 5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.background
                ),
            ) {
                Icon(
                    painterResource(R.drawable.ic_up),
                    contentDescription = "icon up",
                    tint = MaterialTheme.colors.background
                )
            }
        }
    }
}