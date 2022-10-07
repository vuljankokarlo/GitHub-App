package com.assignment.githubapp.common.global.domain.errorHandling

abstract class ErrorHandler {

    companion object {
        internal const val COMMON_ERROR_MSG = "Unknown error"
    }

    protected var next: ErrorHandler? = null

    fun chain(link: ErrorHandler): ErrorHandler {
        next = link
        return link
    }

    abstract fun checkError(throwable: Throwable): String
}