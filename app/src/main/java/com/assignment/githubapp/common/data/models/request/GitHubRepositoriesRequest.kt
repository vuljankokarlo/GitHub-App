package com.assignment.githubapp.common.data.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoriesRequest(
    @SerialName("q")
    val query: String,
    @SerialName("sort")
    val sort: String? = null,
    @SerialName("order")
    val order: String,
    @SerialName("per_page")
    val per_page: Int,
    @SerialName("page")
    val page: Int
)