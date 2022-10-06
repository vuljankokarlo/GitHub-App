package com.assignment.githubapp.features.home.domain.model

import com.assignment.githubapp.common.data.models.response.GitHubRepositoriesWrapperResponse

data class RepositoriesViewState(
    val statusBarHeight: Float = 0f,
    val githubRepositoriesData: GitHubRepositoriesWrapperResponse? = null,
    val repositoryNameQuery: String = "",
    val repositoryNameQueryError: String = ""
)
