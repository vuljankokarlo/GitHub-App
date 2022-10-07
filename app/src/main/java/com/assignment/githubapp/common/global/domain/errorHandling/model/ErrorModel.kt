package com.assignment.githubapp.common.global.domain.errorHandling.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorModel(@SerialName("message") val message: String = "")
