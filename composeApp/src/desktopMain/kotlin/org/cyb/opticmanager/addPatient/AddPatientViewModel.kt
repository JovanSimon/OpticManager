package org.cyb.opticmanager.addPatient

import DoctorReport
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cyb.opticmanager.db.dataModels.PatientData
import org.cyb.opticmanager.initialScreen.repository.DoctorReportRepository
import org.cyb.opticmanager.initialScreen.repository.PatientRepository
import java.time.Clock

class AddPatientViewModel (
    private val patientRepository: PatientRepository,
    private val doctorReportRepository: DoctorReportRepository
): ViewModel() {
    private val _state = MutableStateFlow(AddPatientContract.AddPatientUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: AddPatientContract.AddPatientUiState.()->
        AddPatientContract.AddPatientUiState) = _state.update ( reducer )

    private val events = MutableSharedFlow<AddPatientContract.AddPatientUiEvent>()
    fun setEvent(event: AddPatientContract.AddPatientUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeNavigationIndex()
        observePatientFormChange()
    }

    private fun observePatientFormChange() {
        viewModelScope.launch {
            events
                .onEach { event ->
                    when (event) {
                        is AddPatientContract.AddPatientUiEvent.NameChanged -> {
                            if (!event.value.equals(""))
                                setState { copy(nameError = false) }
                            else
                                setState { copy(nameError = true) }
                            setState { copy(name = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.LastnameChanged -> {
                            if (!event.value.equals(""))
                                setState { copy(lastnameError = false) }
                            else
                                setState { copy(lastnameError = true) }
                            setState { copy(lastname = event.value) }

                        }
                        is AddPatientContract.AddPatientUiEvent.DateOfBirthChanged -> {
                            if (!event.value.equals(""))
                                setState { copy(dateOfBirthError = false) }
                            else
                                setState { copy(dateOfBirthError = true) }
                            setState { copy(dateOfBirth = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.PlaceOfBirthChanged -> {
                            if (!event.value.equals(""))
                                setState { copy(placeOfBirthError = false) }
                            else
                                setState { copy(placeOfBirthError = true) }
                            setState { copy(placeOfBirth = event.value) }
                        }

                        is AddPatientContract.AddPatientUiEvent.SelectedNavigationIndex -> {
                            setState { copy(selectedItemIndex = event.index) }
                        }
                        is AddPatientContract.AddPatientUiEvent.DateOfExamChanged -> {
                            if (!event.value.equals(""))
                                setState { copy(dateOfExamError = false) }
                            else
                                setState { copy(dateOfExamError = true) }
                            setState { copy(dateOfExam = event.value) }
                        }

                        is AddPatientContract.AddPatientUiEvent.OdAxClose -> {
                            setState { copy(odAxClose = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OdAxFar -> {
                            setState { copy(odAxFar = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OdClyClose -> {
                            setState { copy(odClyClose = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OdClyFar -> {
                            setState { copy(odClyFar = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OdSphClose -> {
                            setState { copy(odSphClose = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OdSphFar -> {
                            setState { copy(odSphFar = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OsAxClose -> {
                            setState { copy(osAxClose = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OsAxFar -> {
                            setState { copy(osAxFar = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OsClyClose -> {
                            setState { copy(osClyClose = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OsClyFar -> {
                            setState { copy(osClyFar = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OsSphClose -> {
                            setState { copy(osSphClose = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.OsSphFar -> {
                            setState { copy(osSphFar = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.Description -> {
                            setState { copy(description = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.PdClose -> {
                            setState { copy(pdClose = event.value) }
                        }
                        is AddPatientContract.AddPatientUiEvent.PdFar -> {
                            setState { copy(pdFar = event.value) }
                        }

                        is AddPatientContract.AddPatientUiEvent.SubmitButtonClicked -> {
                            val patient = PatientData (
                                name = state.value.name,
                                lastname = state.value.lastname,
                                dateOfBirth = state.value.dateOfBirth,
                                placeOfLiving = state.value.placeOfBirth
                            )

                            val patientId = patientRepository.insertPatient(patient)


                            val report = DoctorReport(
                                patientId = patientId,
                                odSphFar = state.value.odSphFar,
                                odClyFar = state.value.odClyFar,
                                odAxFar = state.value.odAxFar,
                                osSphFar = state.value.osSphFar,
                                osClyFar = state.value.osClyFar,
                                osAxFar = state.value.osAxFar,
                                pdFar = state.value.pdFar.toInt(),
                                odSphClose = state.value.odSphClose,
                                odClyClose = state.value.odClyClose,
                                odAxClose = state.value.odAxClose,
                                osSphClose = state.value.osSphClose,
                                osClyClose = state.value.osClyClose,
                                osAxClose = state.value.osAxClose,
                                pdClose = state.value.pdClose.toInt(),
                                description = state.value.description
                            )


                            doctorReportRepository.insertDoctorReport(report)
                            resetState()


                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun resetState() {
        setState {
            copy(
                dateOfExam = "",
                name = "",
                lastname = "",
                dateOfBirth = "",
                placeOfBirth = "",
                odSphFar = "",
                odClyFar = "",
                odAxFar = "",
                osSphFar = "",
                osClyFar = "",
                osAxFar = "",
                pdFar = "",
                odSphClose = "",
                odClyClose = "",
                odAxClose = "",
                osSphClose = "",
                osClyClose = "",
                osAxClose = "",
                pdClose = "",
                description = ""
            )
        }
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