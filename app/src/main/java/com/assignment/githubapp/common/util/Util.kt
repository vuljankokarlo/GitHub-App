package com.assignment.githubapp.common.util

import android.content.Context

class Util {
    companion object {
        fun test() = true
    }
}

fun Int.dpFromPx(context: Context): Float {
    return this / context.resources.displayMetrics.density
}