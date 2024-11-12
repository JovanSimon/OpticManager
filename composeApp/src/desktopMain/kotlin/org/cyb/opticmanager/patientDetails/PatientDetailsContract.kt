package org.cyb.opticmanager.patientDetails

import DoctorReport

interface PatientDetailsContract {
    data class PatientDetailsUiState (
        val name: String = "",
        val patientId: String = "",
        val lastname: String = "",
        val dateOfBirth: String = "",
        val placeOfBirth: String = "",
        val patientDataLoading: Boolean = false,
        val doctorReportsLoading: Boolean = false,
        val reports: List<DoctorReport> = emptyList()
    )

    sealed class PatientDetailsUiEvent (

    )
}