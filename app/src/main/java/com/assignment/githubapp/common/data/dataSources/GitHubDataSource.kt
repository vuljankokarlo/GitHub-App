package com.assignment.githubapp.common.data.dataSources

import com.assignment.githubapp.common.data.dataSources.local.GitHubLocalSource
import com.assignment.githubapp.common.data.dataSources.remote.GitHubAPI
import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest
import com.assignment.githubapp.common.data.models.response.GitHubRepositoriesWrapperResponse
import com.assignment.githubapp.common.data.models.response.GitHubRepositoryDetailsResponse
import com.assignment.githubapp.common.data.models.response.GitHubRepositoryResponse
import com.assignment.githubapp.common.data.models.response.OwnerResponse
import com.assignment.githubapp.common.global.domain.errorHandling.ErrorParser
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
        var repositories: GitHubRepositoriesWrapperResponse? = null
        try {
            withContext(Dispatchers.IO) {
                repositories = gitHubAPI.repositories(
                    repositoriesRequest.query,
                    repositoriesRequest.sort,
                    repositoriesRequest.order,
                    repositoriesRequest.per_page,
                    repositoriesRequest.page
                )
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorParser.parseError(e)))
        }
        emit(Resource.Success(repositories))
    }

    suspend fun repositoryDetails(
        repositoryId: Int
    ) = flow {
        var repository: GitHubRepositoryDetailsResponse? = null
        try {
            withContext(Dispatchers.IO) {
                repository = gitHubAPI.repositoryDetails(repositoryId)
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorParser.parseError(e)))
        }
        emit(Resource.Success(repository))
    }

    suspend fun ownerInfo(
        username: String
    ) = flow {
        var owner: OwnerResponse? = null
        try {
            withContext(Dispatchers.IO) {
                owner = gitHubAPI.ownerInfo(username)
            }
        } catch (e: Exception) {
            emit(Resource.Error(ErrorParser.parseError(e)))
        }
        emit(Resource.Success(owner))
    }


    //TODO save/load user
}