package com.assignment.githubapp.common.data.repositories

import com.assignment.githubapp.common.data.dataSources.GitHubDataSource
import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest

class GitHubRepository(
    private val gitHubDataSource: GitHubDataSource
) {
    suspend fun repositories(gitHubRepositoriesRequest: GitHubRepositoriesRequest) =
        gitHubDataSource.repositories(gitHubRepositoriesRequest)
}