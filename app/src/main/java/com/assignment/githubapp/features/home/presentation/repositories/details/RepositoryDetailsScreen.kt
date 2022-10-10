package com.assignment.githubapp.features.home.presentation.repositories.details

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.assignment.githubapp.R
import com.assignment.githubapp.common.data.models.response.GitHubRepositoryDetailsResponse
import com.assignment.githubapp.common.data.models.response.OwnerResponse
import com.assignment.githubapp.common.util.parseToPresentationFormat
import com.assignment.githubapp.features.home.presentation.repositories.components.SecondaryInfoItem
import com.assignment.githubapp.ui.theme.*

@Composable
fun RepositoryDetailsScreen(
    navController: NavController,
    repositoryId: Int,
    viewModel: RepositoryDetailsViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val customTabsIntent = remember {
        mutableStateOf(CustomTabsIntent.Builder().build())
    }


    LaunchedEffect(key1 = Unit) {
        viewModel.initViewModel(repositoryId)
    }

    BackHandler {
        navController.popBackStack()
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Image(
                            rememberAsyncImagePainter(it.owner.avatar_url),
                            contentDescription = "thumbnail image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(top = viewModel.viewState.value.statusBarHeight.dp + 10.dp)
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(bottom = 20.dp)
                        )
                    }

                    item {
                        Text(
                            it.repositoryName,
                            style = MaterialTheme.typography.OpenSansBold_16_24,
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            it.description,
                            style = MaterialTheme.typography.OpenSansRegular_14_20,
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                        )
                    }

                    item {
                        RepositoryInfoSection(
                            it
                        ) {
                            customTabsIntent.value.launchUrl(
                                context,
                                Uri.parse(it.repositoryUrl)
                            )
                        }
                    }

                    item {
                        viewModel.viewState.value.owner?.let { owner ->
                            UserInfoSection(
                                owner
                            ) {
                                customTabsIntent.value.launchUrl(
                                    context,
                                    Uri.parse(it.owner.profileUrl)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserInfoSection(
    owner: OwnerResponse,
    onUserDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = 40.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoItem(label = "Owner name", data = owner.name ?: "")
            InfoItem(label = "Email", data = owner.email ?: "Not provided")
            InfoItem(label = "Followers", data = owner.followers.toString())
            InfoItem(label = "Following", data = owner.following.toString())
            InfoItem(label = "Public repositories", data = owner.publicRepositories.toString())
            Text(
                "See full owner info".uppercase(),
                style = MaterialTheme.typography.OpenSansRegular_10_14,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .clickable {
                        onUserDetailsClick()
                    }
            )
        }
    }
}

@Composable
fun RepositoryInfoSection(
    repository: GitHubRepositoryDetailsResponse,
    onRepositoryDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = 40.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoItem(label = "Created at", data = repository.created_at.parseToPresentationFormat())
            InfoItem(label = "Created at", data = repository.updated_at.parseToPresentationFormat())
            InfoItem(label = "Language", data = repository.language)
            Row(
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                SecondaryInfoItem(repository.forks, R.drawable.ic_forks)
                SecondaryInfoItem(repository.stars, R.drawable.ic_stargazers)
                SecondaryInfoItem(repository.watchers, R.drawable.ic_watchers)
                Spacer(modifier = Modifier.weight(1f))
            }
            Text(
                "See full repository info".uppercase(),
                style = MaterialTheme.typography.OpenSansRegular_10_14,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .clickable {
                        onRepositoryDetailsClick()
                    }
            )
        }
    }
}

@Composable
fun InfoItem(
    label: String,
    data: String
) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.OpenSansBold_14_20,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = data,
            style = MaterialTheme.typography.OpenSansRegular_14_20,
            color = MaterialTheme.colors.primary
        )
    }
}