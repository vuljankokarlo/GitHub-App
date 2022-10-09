package com.assignment.githubapp.common.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerResponse(
    @SerialName("login")
    val name: String,
    @SerialName("avatar_url")
    val avatar_url: String,
    @SerialName("html_url")
    val profileUrl: String
)