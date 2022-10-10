package com.assignment.githubapp.features.home.presentation.repositories

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.assignment.githubapp.common.data.models.request.GitHubRepositoriesRequest
import com.assignment.githubapp.common.global.GlobalRepository
import com.assignment.githubapp.common.util.Resource
import com.assignment.githubapp.common.view.presentation.BaseViewModel
import com.assignment.githubapp.features.home.domain.model.OrderType
import com.assignment.githubapp.features.home.domain.model.RepositoriesViewState
import com.assignment.githubapp.features.home.domain.model.SortType
import com.assignment.githubapp.features.home.domain.useCases.GitHubRepositoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

    sealed class OneShotEvent {
        data class NavigateToResults(val id: Int) : OneShotEvent()
    }

    private var debounceJob: Job? = null

    fun initValues() {
        viewState.value = viewState.value.copy(
            gitHubRepositoriesData = null,
            repositoryList = null,
            isLoadingMore = false,
            repositoryNameQuery = "",
            repositoryNameQueryError = "",
            sort = null,
            order = OrderType.DESC,
            per_page = 10,
            page = 1
        )
        getGitHubRepositories()
    }

    init {
        viewState.value = viewState.value.copy(
            statusBarHeight = globalRepository.statusBarHeight
        )
    }

    private val _oneShotEvents = Channel<OneShotEvent>(Channel.BUFFERED)
    val oneShotEvents = _oneShotEvents.receiveAsFlow()

    fun onQueryFieldValueChange(newValue: String) {
        viewState.value = viewState.value.copy(
            repositoryNameQuery = newValue,
            sort = null,
            order = OrderType.DESC,
            per_page = 10,
            page = 1
        )
        handleDebounce()
    }

    fun navigateToDetails(id: Int) {
        viewModelScope.launch {
            _oneShotEvents.send(OneShotEvent.NavigateToResults(id))
        }
    }

    fun onSortTypeChange(newValue: SortType) {
        viewState.value = viewState.value.copy(
            sort = if (viewState.value.sort == newValue) null else newValue,
            per_page = 10,
            page = 1
        )
        handleDebounce()
    }

    fun onOrderTypeChange() {
        viewState.value = viewState.value.copy(
            order = if (viewState.value.order == OrderType.DESC) OrderType.ASC else OrderType.DESC,
            per_page = 10,
            page = 1
        )
        handleDebounce()
    }

    fun onScrollEnd() {
        if (!viewState.value.isLoadingMore && (viewState.value.gitHubRepositoriesData?.items?.size ?: 0 < viewState.value.gitHubRepositoriesData?.totalCount ?: 0)) {
            viewState.value = viewState.value.copy(
                page = viewState.value.page + 1
            )
            getGitHubRepositories()
        }
    }

    private fun handleDebounce() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(1000)
            viewState.value = viewState.value.copy(
                repositoryList = null
            )
            getGitHubRepositories()
        }
    }

    private fun getGitHubRepositories() {
        viewModelScope.launch {
            viewState.value = viewState.value.copy(
                isLoadingMore = true,
                repositoryNameQueryError = "",
            )
            gitHubRepositoriesUseCases.getRepositoriesUseCase(
                GitHubRepositoriesRequest(
                    query = viewState.value.repositoryNameQuery.ifEmpty {
                        ('A'..'Z').random().toString()
                    },
                    sort = viewState.value.sort.toString(),
                    order = viewState.value.order.toString(),
                    per_page = viewState.value.per_page,
                    page = viewState.value.page
                )
            ).collect { collected ->
                when (collected) {
                    is Resource.Error -> {
                        viewState.value = viewState.value.copy(
                            repositoryNameQueryError = collected.message ?: "",
                            isLoadingMore = false
                        )
                    }
                    is Resource.Success -> {
                        viewState.value = viewState.value.copy(
                            gitHubRepositoriesData = collected.data,
                            repositoryList = ((viewState.value.repositoryList ?: emptyList()).plus(
                                collected.data?.items ?: emptyList()
                            )).distinctBy {
                                it.id
                            },
                            isLoadingMore = false
                        )
                    }
                }
            }
        }
    }
}
