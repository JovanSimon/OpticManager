package org.cyb.opticmanager.initialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cyb.opticmanager.initialScreen.repository.PatientRepository

class InitialScreenViewModel(
    private val patientRepository: PatientRepository,

): ViewModel() {
    private val _state = MutableStateFlow(InitialScreenContract.InitialScreenUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: InitialScreenContract.InitialScreenUiState.() ->
        InitialScreenContract.InitialScreenUiState) = _state.update ( reducer )

    private val events = MutableSharedFlow<InitialScreenContract.InitialScreenUiEvent>()
    fun setEvent(event: InitialScreenContract.InitialScreenUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeSearch()
        observeNavigationIndex()
    }

    private fun observeNavigationIndex() {
        viewModelScope.launch {
            events
                .filterIsInstance<InitialScreenContract.InitialScreenUiEvent.SelectedNavigationIndex>()
                .collect { event ->
                    setState { copy(selectedItemIndex = event.index) }
                }
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            events
                .filterIsInstance<InitialScreenContract.InitialScreenUiEvent.SearchPatients>()
                .collect { event ->
                    val parts = event.value.trim().split(" ")

                    val name = if (parts.isNotEmpty()) parts[0] else null
                    val lastname = if (parts.size > 1) parts[1] else null

//                    println(event.value)
                    setState { copy(loadingPatients = true) }
//                    val nameAndLastname = event.value.lowercase().split(" ")
//                    println(nameAndLastname)
                    val filteredPatients = patientRepository.searchPatients(name, lastname)
//                    println(filteredPatients)
                    setState { copy(patients = filteredPatients) }
                    setState { copy(loadingPatients = false) }
                }

        }
    }

}