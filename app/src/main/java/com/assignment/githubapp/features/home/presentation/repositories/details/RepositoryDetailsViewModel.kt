package com.assignment.githubapp.features.home.presentation.repositories.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.assignment.githubapp.common.global.GlobalRepository
import com.assignment.githubapp.common.util.Resource
import com.assignment.githubapp.common.view.presentation.BaseViewModel
import com.assignment.githubapp.features.home.domain.model.RepositoryDetailsViewState
import com.assignment.githubapp.features.home.domain.useCases.GitHubRepositoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    private val globalRepository: GlobalRepository,
    private val gitHubRepositoriesUseCases: GitHubRepositoriesUseCases
) : BaseViewModel<RepositoryDetailsViewState>() {

    override var viewState: MutableState<RepositoryDetailsViewState> =
        mutableStateOf(RepositoryDetailsViewState())

    fun initViewModel(repositoryId: Int) {
        viewModelScope.launch {
            listOf(
                async { getRepositoryDetails(repositoryId) }
            ).awaitAll()
            getOwner()
        }
    }

    init {
        viewState.value = viewState.value.copy(
            statusBarHeight = globalRepository.statusBarHeight
        )
    }

    private suspend fun getOwner() {
        viewState.value.repository?.let {
            viewState.value = viewState.value.copy(
                errorMessage = ""
            )
            gitHubRepositoriesUseCases.getOwnerInfoUseCase(
                it.owner.login
            ).collect { collected ->
                when (collected) {
                    is Resource.Error -> {
                        viewState.value = viewState.value.copy(
                            errorMessage = collected.message ?: ""
                        )
                    }
                    is Resource.Success -> {
                        viewState.value = viewState.value.copy(
                            owner = collected.data
                        )
                    }
                }
            }
        }
    }

    private suspend fun getRepositoryDetails(repositoryId: Int) {
        gitHubRepositoriesUseCases.getRepositoryDetailsUseCase(
            repositoryId
        ).collect { collected ->
            when (collected) {
                is Resource.Error -> {
                    viewState.value = viewState.value.copy(
                        errorMessage = collected.message ?: ""
                    )
                }
                is Resource.Success -> {
                    viewState.value = viewState.value.copy(
                        repository = collected.data
                    )
                }
            }
        }
    }
}