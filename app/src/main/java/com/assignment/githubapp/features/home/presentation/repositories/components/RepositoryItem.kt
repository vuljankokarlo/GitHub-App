package com.assignment.githubapp.features.home.presentation.repositories.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.assignment.githubapp.BuildConfig
import com.assignment.githubapp.R
import com.assignment.githubapp.common.data.models.response.GitHubRepositoryResponse
import com.assignment.githubapp.common.data.models.response.OwnerResponse
import com.assignment.githubapp.common.util.Util
import com.assignment.githubapp.common.util.Util.Companion.FlavorType.*
import com.assignment.githubapp.common.util.parseToPresentationFormat
import com.assignment.githubapp.ui.theme.OpenSansBold_14_20
import com.assignment.githubapp.ui.theme.OpenSansRegular_10_14
import com.assignment.githubapp.ui.theme.OpenSansRegular_14_20

@Composable
fun RepositoryItem(
    repository: GitHubRepositoryResponse,
    onAvatarClick: (OwnerResponse) -> Unit,
    onDetailsClick: (GitHubRepositoryResponse) -> Unit,
) {
    val isSecondaryInfoShown = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = repository) {
        isSecondaryInfoShown.value = false
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .background(MaterialTheme.colors.background),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(5)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                RepositoryPrimaryInfo(
                    repository = repository,
                    scope = this,
                    onAvatarClick = { onAvatarClick(it) }
                )

                Icon(
                    painterResource(
                        if (isSecondaryInfoShown.value)
                            R.drawable.ic_up
                        else
                            R.drawable.ic_down
                    ),
                    contentDescription = "icon up/down",
                    modifier = Modifier
                        .padding(end = 10.dp, top = 10.dp)
                        .size(24.dp)
                        .clickable {
                            isSecondaryInfoShown.value = !isSecondaryInfoShown.value
                        }
                )
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = isSecondaryInfoShown.value,
                modifier = Modifier
                    .fillMaxWidth(),
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(500)
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(500)
                )
            ) {
                RepositorySecondaryInfo(
                    repository = repository,
                    onDetailsClick = { onDetailsClick(it) })
            }
        }
    }
}

@Composable
fun RepositoryPrimaryInfo(
    repository: GitHubRepositoryResponse,
    scope: RowScope,
    onAvatarClick: (OwnerResponse) -> Unit
) {
    with(scope) {
        Row(
            modifier = Modifier.weight(1f, false),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "Repository name: ",
                        style = MaterialTheme.typography.OpenSansBold_14_20,
                        color = MaterialTheme.colors.primary
                    )
                    Text(
                        repository.repositoryName,
                        style = MaterialTheme.typography.OpenSansRegular_14_20,
                        color = MaterialTheme.colors.primary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        rememberAsyncImagePainter(repository.owner.avatar_url),
                        contentDescription = "thumbnail image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(100.dp)
                            .clickable(
                                enabled = BuildConfig.FLAVOR == PAID.toString()
                            ) {
                                onAvatarClick(repository.owner)
                            }
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                "Owner: ",
                                style = MaterialTheme.typography.OpenSansBold_14_20,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                repository.owner.login,
                                style = MaterialTheme.typography.OpenSansRegular_14_20,
                                color = MaterialTheme.colors.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                "Last updated: ",
                                style = MaterialTheme.typography.OpenSansBold_14_20,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                repository.updated_at.parseToPresentationFormat(),
                                style = MaterialTheme.typography.OpenSansRegular_14_20,
                                color = MaterialTheme.colors.primary
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                "No. of issues: ",
                                style = MaterialTheme.typography.OpenSansBold_14_20,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                repository.openIssues.toString(),
                                style = MaterialTheme.typography.OpenSansRegular_14_20,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepositorySecondaryInfo(
    repository: GitHubRepositoryResponse,
    onDetailsClick: (GitHubRepositoryResponse) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            SecondaryInfoItem(repository.forks, R.drawable.ic_forks)
            SecondaryInfoItem(repository.stars, R.drawable.ic_stargazers)
            SecondaryInfoItem(repository.watchers, R.drawable.ic_watchers)
            Spacer(modifier = Modifier.weight(1f))
        }
        if (BuildConfig.FLAVOR == PAID.toString())
            Text(
                "See full details".uppercase(),
                style = MaterialTheme.typography.OpenSansRegular_10_14,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.clickable {
                    onDetailsClick(repository)
                }
            )
    }
}

@Composable
fun SecondaryInfoItem(
    data: Int,
    icon: Int,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            data.toString(),
            style = MaterialTheme.typography.OpenSansRegular_14_20,
            color = MaterialTheme.colors.primary
        )
    }
}