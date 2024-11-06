package org.cyb.opticmanager.addPatient

interface AddPatientContract {
    data class AddPatientUiState(
        var name: String = "",
        val nameError: Boolean = true,
        var lastname: String = "",
        val lastnameError: Boolean = true,
        var dateOfBirth: String = "",
        val dateOfBirthError: Boolean = true,
        var dateOfExam: String = "",
        val dateOfExamError: Boolean = true,
        var placeOfBirth: String = "",
        val placeOfBirthError: Boolean = true,
        val selectedItemIndex: Int = 1,
        val odSphFar: String = "",
        val odClyFar: String = "",
        val odAxFar: String = "",
        val osSphFar: String = "",
        val osClyFar: String = "",
        val osAxFar: String = "",
        val pdFar: String = "",
        val odSphClose: String = "",
        val odClyClose: String = "",
        val odAxClose: String = "",
        val osSphClose: String = "",
        val osClyClose: String = "",
        val osAxClose: String = "",
        val pdClose: String = "",
        val description: String = ""
    )

    sealed class AddPatientUiEvent {
        data class SelectedNavigationIndex(val index: Int): AddPatientUiEvent()
        data class NameChanged(val value: String): AddPatientUiEvent()
        data class LastnameChanged(val value: String): AddPatientUiEvent()
        data class DateOfBirthChanged(val value: String): AddPatientUiEvent()
        data class PlaceOfBirthChanged(val value: String): AddPatientUiEvent()
        data class DateOfExamChanged(val value: String): AddPatientUiEvent()
        data class OdSphFar(val value: String): AddPatientUiEvent()
        data class OdClyFar(val value: String): AddPatientUiEvent()
        data class OdAxFar(val value: String): AddPatientUiEvent()
        data class OsSphFar(val value: String): AddPatientUiEvent()
        data class OsClyFar(val value: String): AddPatientUiEvent()
        data class OsAxFar(val value: String): AddPatientUiEvent()
        data class PdFar(val value: String): AddPatientUiEvent()
        data class OdSphClose(val value: String): AddPatientUiEvent()
        data class OdClyClose(val value: String): AddPatientUiEvent()
        data class OdAxClose(val value: String): AddPatientUiEvent()
        data class OsSphClose(val value: String): AddPatientUiEvent()
        data class OsClyClose(val value: String): AddPatientUiEvent()
        data class OsAxClose(val value: String): AddPatientUiEvent()
        data class PdClose(val value: String): AddPatientUiEvent()
        data class Description(val value: String): AddPatientUiEvent()
        data class SubmitButtonClicked(val dull: String): AddPatientUiEvent()
    }
}