package com.assignment.githubapp.common.global.domain.errorHandling.handler

import com.assignment.githubapp.common.global.domain.errorHandling.ErrorHandler
import com.assignment.githubapp.common.global.domain.errorHandling.model.ErrorModel
import com.assignment.githubapp.di.NetworkModule
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class NetworkErrorHandler : ErrorHandler() {

    var json: Json = NetworkModule.getBaseJsonConfig()

    override fun checkError(throwable: Throwable): String {
        if (throwable is HttpException) {
            return when (throwable.code()) {
                in 200..399 -> {
                    "OK"
                }
                in 400..499 -> {
                    if (throwable.code() == 404) return "Site not found"
                    val err = kotlin.runCatching {
                        throwable.response()?.errorBody()?.string()
                            ?.let { json.decodeFromString<ErrorModel>(it) }
                    }.onFailure {
                        return throwable.response()?.errorBody()?.string() ?: "Empty error body"
                    }.onSuccess {
                        return it?.message ?: "Empty error msg"
                    }.getOrNull()

                    return err?.message ?: "Empty error msg"
                }
                in 500..599 -> {
                    "Server side error"
                }
                else -> {
                    "Unknown network error"
                }
            }
        }

        return next?.checkError(throwable) ?: COMMON_ERROR_MSG
    }
}