package com.assignment.githubapp.common.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.assignment.githubapp.common.view.navigation.Navigator.Home
import com.assignment.githubapp.common.view.navigation.Navigator.Home.HomeScreenNavRoute
import com.assignment.githubapp.common.view.navigation.Navigator.Home.Profile
import com.assignment.githubapp.common.view.navigation.Navigator.Home.RepositoriesMain
import com.assignment.githubapp.common.view.navigation.Navigator.Home.RepositoryDetails
import com.assignment.githubapp.common.view.navigation.Navigator.Intro
import com.assignment.githubapp.common.view.navigation.Navigator.Intro.SplashScreenRoute
import com.assignment.githubapp.features.home.presentation.bottomnav.HomeScreenNav
import com.assignment.githubapp.features.home.presentation.profile.ProfileScreen
import com.assignment.githubapp.features.home.presentation.repositories.RepositoriesMainScreen
import com.assignment.githubapp.features.home.presentation.repositories.details.RepositoryDetailsScreen
import com.assignment.githubapp.features.intro.presentation.SplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

sealed class Navigator(val route: String) {

    open operator fun invoke() = route

    object Home : Navigator("home") {
        object HomeScreenNavRoute : Navigator("home.homescreenNav")
        object RepositoriesMain : Navigator("home.repositoriesMain")
        object RepositoryDetails : Navigator("home.repositoryDetails")
        object Profile : Navigator("home.profile")
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
            startDestination = HomeScreenNavRoute(),
            route = Home()
        ) {
            composable(route = HomeScreenNavRoute()) { HomeScreenNav(navController) }
            composable(route = RepositoriesMain()) { RepositoriesMainScreen(navController) }
            composable(
                route = "${RepositoryDetails()}{repositoryId}",
                arguments = listOf(navArgument("repositoryId") { defaultValue = -1 })
            ) { backStackEntry ->
                RepositoryDetailsScreen(
                    navController,
                    backStackEntry.arguments?.getInt("repositoryId")!!
                )
            }
            composable(route = Profile()) { ProfileScreen(navController) }
        }
    }
}