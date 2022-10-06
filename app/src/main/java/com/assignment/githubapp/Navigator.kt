package com.assignment.githubapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.assignment.githubapp.Navigator.Home
import com.assignment.githubapp.Navigator.Home.SplashScreenRoute
import com.google.accompanist.pager.ExperimentalPagerApi

sealed class Navigator(val route: String) {

    open operator fun invoke() = route

    object Home : Navigator("home") {
        object SplashScreenRoute : Navigator("home.splashScreen")
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
object Graphs {

    fun NavGraphBuilder.homeGraph(navController: NavHostController) {
        navigation(
            startDestination = SplashScreenRoute(),
            route = Home()
        ) {
            composable(route = SplashScreenRoute()) { SplashScreen(navController) }
        }
    }
}