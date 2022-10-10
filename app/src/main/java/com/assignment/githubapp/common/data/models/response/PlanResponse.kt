package com.assignment.githubapp.common.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanResponse(
    @SerialName("name")
    val name: String,
    @SerialName("space")
    val space: Int,
    @SerialName("collaborators")
    val collaborators: Int,
    @SerialName("private_repos")
    val private_repos: Int
)