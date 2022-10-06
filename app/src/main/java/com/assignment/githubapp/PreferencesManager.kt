package com.assignment.githubapp

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val SHARED_PREFERENCES = "shared_prefs_name"
        private const val STATUS_BAR_HEIGHT = "shared_prefs_name.status_bar_height"
    }

    private val sharePref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun setStatusBarHeight(height: Float) {
        sharePref.edit().putFloat(STATUS_BAR_HEIGHT, height).apply()
    }

    fun getStatusBarHeight() = sharePref.getFloat(STATUS_BAR_HEIGHT, 0f)
}