package org.cyb.opticmanager.addPatient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddPatientViewModel (

): ViewModel() {
    private val _state = MutableStateFlow(AddPatientContract.AddPatientUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: AddPatientContract.AddPatientUiState.()->
        AddPatientContract.AddPatientUiState) = _state.update ( reducer )

    private val events = MutableSharedFlow<AddPatientContract.AddPatientUiEvent>()
    fun setEvent(event: AddPatientContract.AddPatientUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeNavigationIndex()
    }

    private fun observeNavigationIndex() {
        viewModelScope.launch {
            events
                .filterIsInstance<AddPatientContract.AddPatientUiEvent.SelectedNavigationIndex>()
                .collect { event ->
                    setState { copy(selectedItemIndex = event.index) }
                }
        }
    }
}