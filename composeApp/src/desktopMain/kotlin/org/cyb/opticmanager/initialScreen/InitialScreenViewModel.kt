package org.cyb.opticmanager.initialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cyb.opticmanager.db.dataModels.PatientData
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
        fillTable()
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
//                    println(event.value)
                    setState { copy(loadingPatients = true) }
                    val nameAndLastname = event.value.lowercase().split(" ")
//                    println(nameAndLastname)
                    val filteredPatients = patientRepository.getPatientWithNameAndLastname(nameAndLastname[0], nameAndLastname[1])
//                    println(filteredPatients)
                    setState { copy(patients = filteredPatients) }
                    setState { copy(loadingPatients = false) }
                }

        }
    }

    private fun fillTable() {
        viewModelScope.launch {

            patientRepository.clearTable()

            patientRepository.insertPatient(
                PatientData(name = "jovan", lastname = "simonovic", dateOfBirth = "7.3.2002.", placeOfLiving = "Trstenik")
            )

            patientRepository.insertPatient(
                PatientData(name = "Tanja", lastname = "Simonovic", dateOfBirth = "23.3.1994.", placeOfLiving = "Trstenik")
            )

            patientRepository.insertPatient(
                PatientData(name = "Rade", lastname = "Simonovic", dateOfBirth = "10.10.1974.", placeOfLiving = "Aleksandrovac")
            )

            patientRepository.insertPatient(
                PatientData(name = "Dusan", lastname = "Simonovic", dateOfBirth = "25.4.2006.", placeOfLiving = "Beograd")
            )

            patientRepository.getAllPatients().collect { patients ->
                setState { copy(patients = patients) }
            }

//            repository.clearTable()
//
//            repository.insertAppointment(
//                AppointmentData(0, "Jovan", "Simonovic", "1.1.1999.", "Nesto nebitno")
//            )
//            repository.insertAppointment(
//                AppointmentData(0, "Dusan", "Nikolic", "1.1.1999.", "Nesto nebitno2")
//            )
//            repository.insertAppointment(
//                AppointmentData(0, "Tanja", "Radovic", "12.1.1999.", "Nesto nebitno3")
//            )
//            repository.insertAppointment(
//                AppointmentData(0, "Rade", "Petkovic", "5.1.1999.", "Nesto nebitno4")
//            )
//            repository.insertAppointment(
//                AppointmentData(0, "Vito", "Vitomirovic", "2.1.1999.", "Nesto nebitno5")
//            )
//
//            repository.getAllAppointments().collect { appointments ->
//                val name = appointments.size
//                println("Broj termina: $name")
//
//                // Ažuriranje stanja
//                setState { copy(test = "Ime je $name") }
//            }
        }
    }

}