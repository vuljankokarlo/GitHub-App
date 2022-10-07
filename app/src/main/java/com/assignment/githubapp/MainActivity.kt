package com.assignment.githubapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.assignment.githubapp.common.global.GlobalRepository
import com.assignment.githubapp.common.util.dpFromPx
import com.assignment.githubapp.common.view.components.wrappers.AppKeyboardFocusManager
import com.assignment.githubapp.common.view.components.wrappers.SystemBarsWrapper
import com.assignment.githubapp.common.view.navigation.Graphs.homeGraph
import com.assignment.githubapp.common.view.navigation.Graphs.introGraph
import com.assignment.githubapp.common.view.navigation.Navigator
import com.assignment.githubapp.ui.theme.GitHubAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalPagerApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    @Inject
    lateinit var globalRepository: GlobalRepository

    private fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)

        globalRepository.statusBarHeight = statusBarHeight.dpFromPx(applicationContext)

        setContent {
            AppKeyboardFocusManager()
            navController = rememberNavController()
            val isDarkTheme by globalRepository.isDarkTheme.collectAsState()
            GitHubAppTheme(isDarkTheme) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(color = Color.Transparent)
                    systemUiController.setNavigationBarColor(color = Color.Black)
                }
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    SystemBarsWrapper(globalRepository.statusBarHeight) {
                        NavHost(
                            navController = navController,
                            startDestination = getFirstRoute(),
                            modifier = Modifier.navigationBarsPadding()
                        ) {
                            introGraph(navController)
                            homeGraph(navController)
                        }
                    }
                }
            }
        }
    }


    private fun getFirstRoute() = Navigator.Intro()
}

