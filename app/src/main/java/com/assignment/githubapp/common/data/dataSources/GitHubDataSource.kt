package com.assignment.githubapp.common.data.dataSources

import com.assignment.githubapp.common.data.dataSources.local.GitHubLocalSource
import com.assignment.githubapp.common.data.dataSources.remote.GitHubAPI
import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest
import com.assignment.githubapp.common.data.models.response.GitHubRepositoriesResponse
import com.assignment.githubapp.common.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubDataSource @Inject constructor(
    private val gitHubAPI: GitHubAPI,
    private val gitHubLocalSource: GitHubLocalSource
) {
    suspend fun repositories(
        repositoriesRequest: GitHubRepositoriesRequest,
    ) = flow {
        var repositories: GitHubRepositoriesResponse? = null
        try {
            withContext(Dispatchers.IO) {
                repositories = gitHubAPI.repositories(repositoriesRequest)
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
        emit(Resource.Success(repositories))
    }

    //TODO save/load user
}