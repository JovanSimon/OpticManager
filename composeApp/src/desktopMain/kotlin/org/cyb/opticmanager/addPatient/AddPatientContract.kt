package org.cyb.opticmanager.addPatient

interface AddPatientContract {
    data class AddPatientUiState(
        val test: String = "",
        val selectedItemIndex: Int = 1,
    )

    sealed class AddPatientUiEvent {
        data class SelectedNavigationIndex(val index: Int): AddPatientUiEvent()
    }
}