package com.assignment.githubapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GitHubApp : Application() {

    companion object {
        const val TAG = "GitHubApp"
    }

    override fun onCreate() {
        super.onCreate()
    }
}