package com.assignment.githubapp.common.data.repositories

import com.assignment.githubapp.common.data.dataSources.GitHubDataSource
import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest

class GitHubRepository(
    private val gitHubDataSource: GitHubDataSource
) {
    suspend fun repositories(gitHubRepositoriesRequest: GitHubRepositoriesRequest) =
        gitHubDataSource.repositories(gitHubRepositoriesRequest)

    suspend fun repositoryDetails(repositoryId: Int) = gitHubDataSource.repositoryDetails(repositoryId)

    suspend fun ownerInfo(username: String) = gitHubDataSource.ownerInfo(username)
}