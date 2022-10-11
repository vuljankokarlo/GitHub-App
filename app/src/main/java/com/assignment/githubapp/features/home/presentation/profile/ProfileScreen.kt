package com.assignment.githubapp.features.home.presentation.profile

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.assignment.githubapp.BuildConfig
import com.assignment.githubapp.common.util.Util.Companion.FlavorType.FREE
import com.assignment.githubapp.common.view.components.InfoItem
import com.assignment.githubapp.ui.theme.OpenSansBold_14_20
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
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 50.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        rememberAsyncImagePainter(it.avatar_url),
                        contentDescription = "image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(top = viewModel.viewState.value.statusBarHeight.dp + 10.dp)
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .height(250.dp)
                            .padding(bottom = 20.dp)
                    )
                    Text(
                        it.name ?: "",
                        style = MaterialTheme.typography.OpenSansBold_16_24,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()
                    )

                    InfoItem(label = "Email", data = it.email ?: "Not provided")
                    InfoItem(label = "Followers", data = it.followers.toString())
                    InfoItem(label = "Following", data = it.following.toString())
                    InfoItem(label = "Public repositories", data = it.publicRepositories.toString())
                    InfoItem(
                        label = "Private repositories",
                        data = it.plan?.private_repos.toString()
                    )
                    InfoItem(label = "Plan", data = it.plan?.name ?: "")
                    InfoItem(label = "Profile type", data = it.type ?: "")
                    InfoItem(label = "Collaborators", data = it.plan?.collaborators.toString())
                    InfoItem(label = "Free space", data = it.plan?.space.toString())
                    InfoItem(label = "Disk usage", data = it.diskUsage.toString())

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        modifier = Modifier.padding(top = 30.dp),
                        onClick = {
                            auth.signOut()
                            viewModel.signOut()
                        },
                        shape = CircleShape,
                        elevation = ButtonDefaults.elevation(10.dp, 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.background
                        ),
                        contentPadding = PaddingValues(20.dp)
                    ) {
                        Text(
                            "Logout".uppercase(),
                            style = MaterialTheme.typography.OpenSansBold_14_20,
                            color = MaterialTheme.colors.background,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
    }
}