package com.assignment.githubapp.common.data.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoriesRequest(
    @SerialName("q")
    val query: String
)