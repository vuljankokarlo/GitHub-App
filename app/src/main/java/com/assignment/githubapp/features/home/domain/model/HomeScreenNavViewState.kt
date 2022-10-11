package com.assignment.githubapp.features.home.domain.model

import androidx.annotation.DrawableRes
import com.assignment.githubapp.R
import com.assignment.githubapp.common.view.navigation.Navigator
import kotlinx.coroutines.flow.StateFlow

data class HomeScreenNavViewState(
    val isDarkTheme: StateFlow<Boolean>? = null
)

sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int) {
    object Repositories :
        Screen(Navigator.Home.RepositoriesMain.route, "Home", R.drawable.ic_git_svgrepo_com)

    object Profile :
        Screen(Navigator.Home.Profile.route, "Profile", R.drawable.ic_profile_svgrepo_com)

    object Empty : Screen("", "", R.drawable.ic_close_40px)
}

enum class SheetContent {
    Empty,
    FabModal
}