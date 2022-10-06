package com.assignment.githubapp.common.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.assignment.githubapp.common.view.navigation.Navigator.Home
import com.assignment.githubapp.common.view.navigation.Navigator.Home.RepositoriesDetails
import com.assignment.githubapp.common.view.navigation.Navigator.Home.RepositoriesMain
import com.assignment.githubapp.common.view.navigation.Navigator.Intro
import com.assignment.githubapp.common.view.navigation.Navigator.Intro.SplashScreenRoute
import com.assignment.githubapp.features.home.presentation.repositories.RepositoriesMainScreen
import com.assignment.githubapp.features.home.presentation.repositories.details.RepositoriesDetailsScreen
import com.assignment.githubapp.features.intro.presentation.SplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

sealed class Navigator(val route: String) {

    open operator fun invoke() = route

    object Home : Navigator("home") {
        object RepositoriesMain : Navigator("home.repositoriesMain")
        object RepositoriesDetails : Navigator("home.repositoriesDetails")
    }

    object Intro : Navigator("intro") {
        object SplashScreenRoute : Navigator("intro.splashScreen")
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
object Graphs {

    fun NavGraphBuilder.introGraph(navController: NavHostController) {
        navigation(
            startDestination = SplashScreenRoute(),
            route = Intro()
        ) {
            composable(route = SplashScreenRoute()) { SplashScreen(navController) }
        }
    }

    fun NavGraphBuilder.homeGraph(navController: NavHostController) {
        navigation(
            startDestination = RepositoriesMain(),
            route = Home()
        ) {
            composable(route = RepositoriesMain()) { RepositoriesMainScreen(navController) }
            composable(route = RepositoriesDetails()) { RepositoriesDetailsScreen(navController) }
        }
    }
}