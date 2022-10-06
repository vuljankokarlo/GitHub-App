package com.assignment.githubapp.common.global

import com.assignment.githubapp.common.PreferencesManager
import com.assignment.githubapp.common.global.domain.useCases.GlobalRepositoryUseCases
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
}