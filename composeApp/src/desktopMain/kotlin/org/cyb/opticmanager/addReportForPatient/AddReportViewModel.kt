package org.cyb.opticmanager.addReportForPatient

import DoctorReport
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cyb.opticmanager.initialScreen.repository.DoctorReportRepository

class AddReportViewModel (
    private val patientId: String,
    private val doctorReportRepository: DoctorReportRepository
): ViewModel() {
    private val _state = MutableStateFlow(AddReportContract.AddReportUiState())
    val state = _state.asStateFlow()
    val ogPatientId = patientId

    private fun setState(reducer: AddReportContract.AddReportUiState.() ->
    AddReportContract.AddReportUiState) = _state.update(reducer)

    private val events = MutableSharedFlow<AddReportContract.AddReportUiEvent>()
    fun setEvent(event: AddReportContract.AddReportUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeReportChange()
    }

    private fun observeReportChange() {
        viewModelScope.launch {
            events
                .onEach { event ->
                    when(event) {
                        is AddReportContract.AddReportUiEvent.DateOfExamChanged -> {
                            setState { copy(dateOfExam = event.value) }
                        }

                        is AddReportContract.AddReportUiEvent.Description -> {
                            setState { copy(description = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OdAxClose -> {
                            setState { copy(odAxClose = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OdAxFar -> {
                            setState { copy(odAxFar = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OdClyClose -> {
                            setState { copy(odClyClose = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OdClyFar -> {
                            setState { copy(odClyFar = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OdSphClose -> {
                            setState { copy(odSphClose = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OdSphFar -> {
                            setState { copy(odSphFar = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OsAxClose -> {
                            setState { copy(osAxClose = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OsAxFar -> {
                            setState { copy(osAxFar = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OsClyClose -> {
                            setState { copy(osClyClose = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OsClyFar -> {
                            setState { copy(osClyFar = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OsSphClose -> {
                            setState { copy(osSphClose = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.OsSphFar -> {
                            setState { copy(osSphFar = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.PdClose -> {
                            setState { copy(pdClose = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.PdFar -> {
                            setState { copy(pdFar = event.value) }
                        }
                        is AddReportContract.AddReportUiEvent.SubmitButtonClicked -> {
                            val report = DoctorReport(
                                patientId = patientId.toLong(),
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
                                dateOfReport = state.value.dateOfExam,
                                description = state.value.description
                            )

                            doctorReportRepository.insertDoctorReport(report)
                            resetState()
                            setState { copy(navigateBack = true, patientId = ogPatientId) }
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
}