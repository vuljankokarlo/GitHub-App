package com.assignment.githubapp.features.home.domain.model

import com.assignment.githubapp.common.data.models.response.OwnerResponse

data class ProfileViewState(
    val statusBarHeight: Float = 0f,
    val accessToken: String? = null,
    val errorMessage: String? = null,
    val userInfo: OwnerResponse? = null
)