package com.assignment.githubapp.features.home.presentation.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.assignment.githubapp.common.global.GlobalRepository
import com.assignment.githubapp.common.util.Resource
import com.assignment.githubapp.common.view.presentation.BaseViewModel
import com.assignment.githubapp.features.home.domain.model.ProfileViewState
import com.assignment.githubapp.features.home.domain.useCases.GitHubRepositoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val globalRepository: GlobalRepository,
    private val gitHubRepositoriesUseCases: GitHubRepositoriesUseCases
) : BaseViewModel<ProfileViewState>() {

    override var viewState: MutableState<ProfileViewState> =
        mutableStateOf(ProfileViewState())

    init {
        viewState.value = viewState.value.copy(
            statusBarHeight = globalRepository.statusBarHeight,
            accessToken = globalRepository.accessToken
        )
    }

    fun setAccessToken(token: String) {
        globalRepository.accessToken = token
        getOwnerInfo()
    }

    fun signOut() {
        viewState.value = viewState.value.copy(
            accessToken = "",
            userInfo = null
        )
    }

    private fun getOwnerInfo() {
        viewModelScope.launch {
            gitHubRepositoriesUseCases.getAuthOwnerInfoUseCase().collect { collected ->
                when (collected) {
                    is Resource.Error -> {
                        viewState.value = viewState.value.copy(
                            errorMessage = collected.message
                        )
                    }
                    is Resource.Success -> {
                        viewState.value = viewState.value.copy(
                            userInfo = collected.data
                        )
                    }
                }

            }
        }
    }
}