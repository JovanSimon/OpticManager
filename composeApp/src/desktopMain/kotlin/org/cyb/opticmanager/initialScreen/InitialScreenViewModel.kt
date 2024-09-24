package org.cyb.opticmanager.initialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.cyb.opticmanager.db.dataModels.AppointmentData
import org.cyb.opticmanager.initialScreen.repository.AppointmentRepository

class InitialScreenViewModel(
    private val repository: AppointmentRepository
): ViewModel() {
    private val _state = MutableStateFlow(InitialScreenContract.InitialScreenUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: InitialScreenContract.InitialScreenUiState.() ->
        InitialScreenContract.InitialScreenUiState) = _state.update ( reducer )

    private val events = MutableSharedFlow<InitialScreenContract.InitialScreenUiEvent>()
    fun setEvent(event: InitialScreenContract.InitialScreenUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        fillTable()

    }

    private fun fillTable() {
        viewModelScope.launch {

            repository.clearTable()

            repository.insertAppointment(
                AppointmentData(0, "Jovan", "Simonovic", "1.1.1999.", "Nesto nebitno")
            )
            repository.insertAppointment(
                AppointmentData(0, "Dusan", "Nikolic", "1.1.1999.", "Nesto nebitno2")
            )
            repository.insertAppointment(
                AppointmentData(0, "Tanja", "Radovic", "12.1.1999.", "Nesto nebitno3")
            )
            repository.insertAppointment(
                AppointmentData(0, "Rade", "Petkovic", "5.1.1999.", "Nesto nebitno4")
            )
            repository.insertAppointment(
                AppointmentData(0, "Vito", "Vitomirovic", "2.1.1999.", "Nesto nebitno5")
            )

            repository.getAllAppointments().collect { appointments ->
                val name = appointments.size
                println("Broj termina: $name")

                // Ažuriranje stanja
                setState { copy(test = "Ime je $name") }
            }
        }
    }

}