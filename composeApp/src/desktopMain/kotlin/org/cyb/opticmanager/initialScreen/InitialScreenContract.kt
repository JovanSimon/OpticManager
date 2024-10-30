package org.cyb.opticmanager.initialScreen

import org.cyb.opticmanager.db.dataModels.PatientData

interface InitialScreenContract {
    data class InitialScreenUiState(
        val selectedItemIndex: Int = 0,
        val loadingPatients: Boolean = false,
        val patients: List<PatientData> = emptyList()
    )

    sealed class InitialScreenUiError {
        data class FaildToTest(val cause: Throwable? = null): InitialScreenUiError()
    }

    sealed class InitialScreenUiEvent {
        data class SearchPatients(val value: String): InitialScreenUiEvent()
    }
}