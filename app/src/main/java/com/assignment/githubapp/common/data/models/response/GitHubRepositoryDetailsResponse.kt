package com.assignment.githubapp.common.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoryDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val repositoryName: String,
    @SerialName("owner")
    val owner: OwnerResponse,
    @SerialName("stargazers_count")
    val stars: Int,
    @SerialName("watchers_count")
    val watchers: Int,
    @SerialName("forks")
    val forks: Int,
    @SerialName("updated_at")
    val updated_at: String,
    @SerialName("open_issues_count")
    val openIssues: Int,
    @SerialName("html_url")
    val repositoryUrl: String,
    @SerialName("language")
    val language: String,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("description")
    val description: String
)