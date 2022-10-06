package com.assignment.githubapp.common.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoriesWrapperResponse(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("items")
    val items: List<GitHubRepositoryResponse>
)