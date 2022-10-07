package com.assignment.githubapp.common.global

import com.assignment.githubapp.common.PreferencesManager
import com.assignment.githubapp.common.global.domain.useCases.GlobalRepositoryUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GlobalRepository @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val globalRepositoryUseCases: GlobalRepositoryUseCases
) {
    var statusBarHeight: Float
        get() = preferencesManager.getStatusBarHeight()
        set(newStatusBarHeight) {
            preferencesManager.setStatusBarHeight(newStatusBarHeight)
        }

    private val _isDarkTheme = MutableStateFlow(preferencesManager.getDarkTheme())
    val isDarkTheme = _isDarkTheme.asStateFlow()

    fun setDarkTheme(isDarkTheme: Boolean) {
        preferencesManager.setDarkTheme(isDarkTheme)
        _isDarkTheme.value = isDarkTheme
    }

}