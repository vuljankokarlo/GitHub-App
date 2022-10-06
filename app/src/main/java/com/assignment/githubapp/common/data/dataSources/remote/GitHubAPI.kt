package com.assignment.githubapp.common.data.dataSources.remote

import com.assignment.githubapp.common.data.models.response.GitHubRepositoriesWrapperResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubAPI {
    @GET("/search/repositories")
    suspend fun repositories(
        @Query("q") repositoryName: String,
    ): GitHubRepositoriesWrapperResponse
}