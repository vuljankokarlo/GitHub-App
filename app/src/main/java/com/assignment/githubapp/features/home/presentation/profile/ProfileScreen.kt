package com.assignment.githubapp.features.home.presentation.profile

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.assignment.githubapp.BuildConfig
import com.assignment.githubapp.common.util.Util.Companion.FlavorType.FREE
import com.assignment.githubapp.ui.theme.OpenSansBold_16_24
import com.assignment.githubapp.ui.theme.OpenSansRegular_14_20
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val auth = FirebaseAuth.getInstance()
    auth.getAccessToken(true)
    val intent = AuthUI.getInstance().createSignInIntentBuilder()
        .setAvailableProviders(
            listOf(
                AuthUI.IdpConfig.GitHubBuilder()
                    .setScopes(listOf("public_repo", "read:user"))
                    .build()
            )
        )
        .build()

    val launcher =
        rememberLauncherForActivityResult(contract = FirebaseAuthUIActivityResultContract()) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    viewModel.setAccessToken(it.idpResponse?.idpToken.toString())
                }
                else -> null
            }
        }


    LaunchedEffect(key1 = Unit) {
        if (!viewModel.viewState.value.accessToken.isNullOrEmpty()) {
            launcher.launch(intent)
        }
    }

    if (BuildConfig.FLAVOR == FREE.toString()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .align(Alignment.Center),
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                elevation = 5.dp
            ) {
                Text(
                    "In order to use all features please buy the full version of the app. " +
                            "Or just... build the project with productFlavor: paid",
                    style = MaterialTheme.typography.OpenSansRegular_14_20,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    } else {
        if (viewModel.viewState.value.userInfo == null)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "authorize to continue".uppercase(),
                    style = MaterialTheme.typography.OpenSansBold_16_24,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable {
                            launcher.launch(intent)
                        }
                )
            }
        else
            viewModel.viewState.value.userInfo?.let {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        it.plan?.name ?: "",
                        style = MaterialTheme.typography.OpenSansBold_16_24,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .clickable {
                                auth.signOut()
                                viewModel.signOut()
                            }
                    )
                }
            }
    }
}