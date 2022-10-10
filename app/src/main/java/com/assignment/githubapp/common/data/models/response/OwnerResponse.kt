package com.assignment.githubapp.common.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerResponse(
    @SerialName("login")
    val login: String,
    @SerialName("avatar_url")
    val avatar_url: String,
    @SerialName("html_url")
    val profileUrl: String,
    @SerialName("public_repos")
    val publicRepositories: Int? = null,
    @SerialName("followers")
    val followers: Int? = null,
    @SerialName("following")
    val following: Int? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("name")
    val name: String? = null,
)