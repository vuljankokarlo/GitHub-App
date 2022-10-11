package com.assignment.githubapp.features.home.presentation.bottomnav

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.assignment.githubapp.R
import com.assignment.githubapp.common.view.navigation.Navigator
import com.assignment.githubapp.features.home.domain.model.Screen
import com.assignment.githubapp.features.home.domain.model.SheetContent.Empty
import com.assignment.githubapp.features.home.domain.model.SheetContent.FabModal
import com.assignment.githubapp.features.home.presentation.profile.ProfileScreen
import com.assignment.githubapp.features.home.presentation.repositories.RepositoriesMainScreen
import com.assignment.githubapp.ui.theme.OpenSansRegular_10_14
import com.assignment.githubapp.ui.theme.OpenSansRegular_14_20
import com.assignment.githubapp.ui.theme.Turquoise
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class
)
@Composable
fun HomeScreenNav(
    mainNavController: NavController,
    viewModel: HomeScreenNavViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val navController = rememberNavController()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    BackHandler(viewModel.backHandlerEnabled.value) {
        coroutineScope.launch { modalBottomSheetState.hide() }
        viewModel.backHandlerEnabled.value = false
    }


    DisposableEffect(key1 = Unit, effect = {
        coroutineScope.launch { modalBottomSheetState.hide() }
        onDispose { }
    })

    LaunchedEffect(key1 = Unit) {
        viewModel.initValues()
    }


    LaunchedEffect(key1 = Unit) {
        viewModel.oneShotEvents
            .onEach {
                when (it) {
                    is HomeScreenNavViewModel.OneShotEvent.BottomNavigation -> {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
            .collect()
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
        sheetElevation = 0.dp,
        sheetBackgroundColor = MaterialTheme.colors.background,
        scrimColor = MaterialTheme.colors.primary.copy(alpha = 0.6f),
        sheetContent = {
            //compose quick fix
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            when (viewModel.sheetContent.value) {
                Empty -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.White)
                    )
                    viewModel.backHandlerEnabled.value = false
                }
                FabModal -> {
                    viewModel.backHandlerEnabled.value = true
                    FabModal(
                        scope = coroutineScope,
                        modalBottomSheetState = modalBottomSheetState,
                        viewModel = viewModel
                    )
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.background,
                    elevation = 0.dp
                ) {
                    BottomNav(navController = navController) {
                        viewModel.navigate(it)
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                ) {
                    FloatingActionButton(
                        shape = CircleShape,
                        backgroundColor = MaterialTheme.colors.background,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colors.background),
                                CircleShape
                            )
                            .size(56.dp),
                        onClick = {
                            viewModel.sheetContent.value = FabModal
                            coroutineScope.launch { modalBottomSheetState.show() }
                        },
                        contentColor = MaterialTheme.colors.primary
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        ) {
            if (!modalBottomSheetState.isVisible && !modalBottomSheetState.isAnimationRunning)
                if (viewModel.sheetContent.value != Empty)
                    viewModel.sheetContent.value = Empty

            MainScreenNavigation(
                navController,
                mainNavController,
            )
        }
    }
}

@Composable
fun BottomNav(navController: NavController, navigate: (String) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val items = mutableListOf(
        Screen.Repositories,
        Screen.Empty,
        Screen.Profile,
    )
    BottomNavigation(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "",
                        tint = if (item.route == "") Color.Transparent else LocalContentColor.current.copy(
                            alpha = LocalContentAlpha.current
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.OpenSansRegular_10_14,
                        modifier = Modifier.wrapContentWidth()
                    )
                },
                selected = currentRoute?.hierarchy?.any { it.route == item.route } == true,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.primary.copy(alpha = 0.4f),
                enabled = item.route != "",
                onClick = {
                    navigate(item.route)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenNavigation(
    navController: NavHostController,
    mainNavController: NavController,
) {

    NavHost(navController, startDestination = Screen.Repositories.route) {
        composable(route = Navigator.Home.RepositoriesMain()) {
            RepositoriesMainScreen(
                navController = mainNavController
            )
        }

        composable(route = Navigator.Home.Profile()) {
            ProfileScreen(
                mainNavController
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FabModal(
    scope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    viewModel: HomeScreenNavViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val isDarkTheme = viewModel.viewState.value.isDarkTheme?.collectAsState()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Dark mode".uppercase(),
                style = MaterialTheme.typography.OpenSansRegular_14_20,
                color = MaterialTheme.colors.primary
            )
            Switch(
                checked = isDarkTheme?.value ?: false,
                onCheckedChange = {
                    viewModel.setDarkTheme(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary,
                    uncheckedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = Turquoise,
                    uncheckedTrackColor = Turquoise,
                ),
                modifier = Modifier.scale(1.2f)
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_close_40px),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp)
                .clickable {
                    scope.launch { modalBottomSheetState.hide() }
                },
            tint = Turquoise
        )
    }
}