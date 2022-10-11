package com.assignment.githubapp.features.home.presentation.bottomnav

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.assignment.githubapp.common.global.GlobalRepository
import com.assignment.githubapp.common.view.presentation.BaseViewModel
import com.assignment.githubapp.features.home.domain.model.HomeScreenNavViewState
import com.assignment.githubapp.features.home.domain.model.SheetContent.Empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenNavViewModel @Inject constructor(
    private val globalRepository: GlobalRepository,
) : BaseViewModel<HomeScreenNavViewState>() {

    override var viewState: MutableState<HomeScreenNavViewState> =
        mutableStateOf(HomeScreenNavViewState())

    sealed class OneShotEvent {
        data class BottomNavigation(val route: String) : OneShotEvent()
    }

    private val _oneShotEvents = Channel<OneShotEvent>(Channel.BUFFERED)
    val oneShotEvents = _oneShotEvents.receiveAsFlow()

    val backHandlerEnabled = mutableStateOf(false)

    val sheetContent = mutableStateOf(Empty)

    fun initValues() {
        sheetContent.value = Empty
        backHandlerEnabled.value = false
    }

    init {
        viewState.value = viewState.value.copy(
            isDarkTheme = globalRepository.isDarkTheme
        )
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        globalRepository.setDarkTheme(isDarkTheme)
    }

    fun navigate(route: String) {
        viewModelScope.launch {
            _oneShotEvents.send(OneShotEvent.BottomNavigation(route))
        }
    }


}