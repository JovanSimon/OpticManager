package org.cyb.opticmanager.patientDetails

interface PatientDetailsContract {
    data class PatientDetailsUiState (
        val name: String = "",
        val lastname: String = "",
        val dateOfBirth: String = "",
        val placeOfBirth: String = "",
        val patientDataLoading: Boolean = false
    )

    sealed class PatientDetailsUiEvent (

    )
}