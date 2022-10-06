package com.assignment.githubapp.common.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoriesResponse(
    @SerialName("total_count")
    val totalCount: Int
)