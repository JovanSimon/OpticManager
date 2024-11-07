package org.cyb.opticmanager.patientDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cyb.opticmanager.initialScreen.repository.PatientRepository
import org.cyb.opticmanager.navigation.patientId

class PatientDetailsViewModel (
    private val patientId: String,
    private val patientRepository: PatientRepository
): ViewModel() {

    private val _state = MutableStateFlow(PatientDetailsContract.PatientDetailsUiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: PatientDetailsContract.PatientDetailsUiState.() ->
    PatientDetailsContract.PatientDetailsUiState) = _state.update(reducer)

    private val events = MutableSharedFlow<PatientDetailsContract.PatientDetailsUiEvent>()
    fun setEvent(event: PatientDetailsContract.PatientDetailsUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        println("Patient ID: ${patientId.toLong()}")
        populatePatientInfo()
    }

    private fun populatePatientInfo() {
        setState { copy(patientDataLoading = true) }
        viewModelScope.launch {
            val patient = patientRepository.getPatientById(patientId.toLong())

            setState { copy(name = patient.name, lastname = patient.lastname,
                placeOfBirth = patient.placeOfLiving, dateOfBirth = patient.dateOfBirth) }
        }
        setState { copy(patientDataLoading = false) }
    }
}