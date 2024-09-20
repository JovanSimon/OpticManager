package org.cyb.opticmanager.initialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InitialScreenViewModel(

): ViewModel() {
    private val _state = MutableStateFlow(InitialScreenContract.InitialScreenUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: InitialScreenContract.InitialScreenUiState.() ->
        InitialScreenContract.InitialScreenUiState) = _state.update ( reducer )

    private val events = MutableSharedFlow<InitialScreenContract.InitialScreenUiEvent>()
    fun setEvent(event: InitialScreenContract.InitialScreenUiEvent) = viewModelScope.launch { events.emit(event) }

    init {

    }

}