package com.assignment.githubapp.features.home.presentation.repositories.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.assignment.githubapp.common.data.models.response.GitHubRepositoryResponse
import com.assignment.githubapp.ui.theme.OpenSansRegular_16_24

@Composable
fun RepositoryItem(
    repository: GitHubRepositoryResponse,
    onAvatarClick: (String) -> Unit,
    onDetailsClick: (GitHubRepositoryResponse) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            repository.repositoryName,
            style = MaterialTheme.typography.OpenSansRegular_16_24,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Start
        )
    }
}