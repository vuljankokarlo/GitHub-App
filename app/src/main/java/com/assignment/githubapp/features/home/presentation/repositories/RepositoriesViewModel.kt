package com.assignment.githubapp.features.home.presentation.repositories

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.githubapp.GitHubApp
import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest
import com.assignment.githubapp.common.global.GlobalRepository
import com.assignment.githubapp.common.util.Resource
import com.assignment.githubapp.common.view.presentation.BaseViewModel
import com.assignment.githubapp.features.home.domain.model.RepositoriesViewState
import com.assignment.githubapp.features.home.domain.useCases.GitHubRepositoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val globalRepository: GlobalRepository,
    private val gitHubRepositoriesUseCases: GitHubRepositoriesUseCases
) : BaseViewModel<RepositoriesViewState>() {

    override var viewState: MutableState<RepositoriesViewState> =
        mutableStateOf(RepositoriesViewState())

    init {
        viewState.value = viewState.value.copy(
            statusBarHeight = globalRepository.statusBarHeight
        )
        getGitHubRepositories()
    }

    fun onQueryFieldValueChange() {
        getGitHubRepositories()
    }

    private fun getGitHubRepositories() {
        viewModelScope.launch {
            gitHubRepositoriesUseCases.getRepositoriesUseCase(
                GitHubRepositoriesRequest(
                    query = viewState.value.repositoryNameQuery.ifEmpty {
                        ('A'..'Z').random().toString()
                    }
                )
            ).collect { collected ->
                when (collected) {
                    is Resource.Error -> {
                        //TODO show error
                    }
                    is Resource.Success -> {
                        viewState.value = viewState.value.copy(
                            githubRepositoriesData = collected.data
                        )
                    }
                }
            }
        }
    }
}