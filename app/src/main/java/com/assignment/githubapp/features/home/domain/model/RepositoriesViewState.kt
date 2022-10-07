package com.assignment.githubapp.features.home.domain.model

import com.assignment.githubapp.common.data.models.response.GitHubRepositoriesWrapperResponse
import com.assignment.githubapp.common.data.models.response.GitHubRepositoryResponse

data class RepositoriesViewState(
    val statusBarHeight: Float = 0f,
    val isLoadingMore: Boolean = false,
    val gitHubRepositoriesData: GitHubRepositoriesWrapperResponse? = null,
    val repositoryList: List<GitHubRepositoryResponse>? = null,
    val repositoryNameQuery: String = "",
    val repositoryNameQueryError: String = "",

    val sort: SortType? = null,
    val order: OrderType = OrderType.DESC,
    val per_page: Int = 10,
    val page: Int = 1
)


enum class OrderType {
    DESC,
    ASC;

    override fun toString(): String {
        return name.lowercase()
    }
}

enum class SortType {
    STARS,
    FORKS,
    UPDATED;

    override fun toString(): String {
        return name.lowercase()
    }
}