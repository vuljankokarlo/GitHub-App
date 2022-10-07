package com.assignment.githubapp.common.global.domain.errorHandling

import com.assignment.githubapp.common.global.domain.errorHandling.handler.NetworkErrorHandler

object ErrorParser {
    private var chain: ErrorHandler? = null
        get() {
            if (field == null) prepareChain()
            return field
        }

    private fun prepareChain() {
        val first = NetworkErrorHandler()
        chain = first
        //TODO add to chain
        //first.chain(ErrorHandler())
    }

    fun parseError(throwable: Throwable): String = chain!!.checkError(throwable)
}