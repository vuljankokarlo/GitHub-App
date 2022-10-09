package com.assignment.githubapp.features.home.domain.model

import com.assignment.githubapp.common.data.models.response.GitHubRepositoryResponse

data class RepositoryDetailsViewState(
    val isLoading: Boolean = false,
    val repository: GitHubRepositoryResponse? = null
)