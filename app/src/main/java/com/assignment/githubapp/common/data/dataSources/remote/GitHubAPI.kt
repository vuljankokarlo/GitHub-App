package com.assignment.githubapp.common.data.dataSources.remote

import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest
import com.assignment.githubapp.common.data.models.response.GitHubRepositoriesResponse
import retrofit2.http.Body
import retrofit2.http.GET


interface GitHubAPI {
    @GET("/search/repositories")
    suspend fun repositories(
        @Body body: GitHubRepositoriesRequest
    ): GitHubRepositoriesResponse
}