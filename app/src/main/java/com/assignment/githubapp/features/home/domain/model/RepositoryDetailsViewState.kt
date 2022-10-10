package com.assignment.githubapp.features.home.domain.model

import com.assignment.githubapp.common.data.models.response.GitHubRepositoryDetailsResponse
import com.assignment.githubapp.common.data.models.response.OwnerResponse

data class RepositoryDetailsViewState(
    val statusBarHeight: Float = 0f,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val repository: GitHubRepositoryDetailsResponse? = null,
    val owner: OwnerResponse? = null
)