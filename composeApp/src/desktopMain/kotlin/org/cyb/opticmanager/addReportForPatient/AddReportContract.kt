package org.cyb.opticmanager.addReportForPatient

interface AddReportContract {
    data class AddReportUiState (
        var dateOfExam: String = "",
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
        val description: String = "",
        val navigateBack: Boolean = false,
        val patientId: String = ""
    )

    sealed class AddReportUiEvent {
        data class DateOfExamChanged(val value: String): AddReportUiEvent()
        data class OdSphFar(val value: String) : AddReportUiEvent()
        data class OdClyFar(val value: String) : AddReportUiEvent()
        data class OdAxFar(val value: String) : AddReportUiEvent()
        data class OsSphFar(val value: String) : AddReportUiEvent()
        data class OsClyFar(val value: String) : AddReportUiEvent()
        data class OsAxFar(val value: String) : AddReportUiEvent()
        data class PdFar(val value: String) : AddReportUiEvent()
        data class OdSphClose(val value: String) : AddReportUiEvent()
        data class OdClyClose(val value: String) : AddReportUiEvent()
        data class OdAxClose(val value: String) : AddReportUiEvent()
        data class OsSphClose(val value: String) : AddReportUiEvent()
        data class OsClyClose(val value: String) : AddReportUiEvent()
        data class OsAxClose(val value: String) : AddReportUiEvent()
        data class PdClose(val value: String) : AddReportUiEvent()
        data class Description(val value: String) : AddReportUiEvent()
        data class SubmitButtonClicked(val dull: String) : AddReportUiEvent()
    }
}