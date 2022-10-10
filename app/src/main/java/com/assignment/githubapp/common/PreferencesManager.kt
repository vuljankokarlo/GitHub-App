package com.assignment.githubapp.common

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val SHARED_PREFERENCES = "shared_prefs_name"
        private const val STATUS_BAR_HEIGHT = "shared_prefs_name.status_bar_height"
        private const val DARK_THEME = "shared_prefs_name.dark_theme"
        private const val ACCESS_TOKEN = "shared_prefs_name.access_token"
    }

    private val sharePref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun setStatusBarHeight(height: Float) {
        sharePref.edit().putFloat(STATUS_BAR_HEIGHT, height).apply()
    }

    fun getStatusBarHeight() = sharePref.getFloat(STATUS_BAR_HEIGHT, 0f)

    fun setDarkTheme(isDarkTheme: Boolean) {
        sharePref.edit().putBoolean(DARK_THEME, isDarkTheme).apply()
    }

    fun getDarkTheme() = sharePref.getBoolean(DARK_THEME, false)

    fun setAccessToken(token: String) {
        sharePref.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken() = sharePref.getString(ACCESS_TOKEN, "")
}