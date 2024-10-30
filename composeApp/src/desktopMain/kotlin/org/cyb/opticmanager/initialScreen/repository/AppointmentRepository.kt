package org.cyb.opticmanager.initialScreen.repository

import kotlinx.coroutines.flow.Flow
import org.cyb.opticmanager.db.AppDatabase
import org.cyb.opticmanager.db.dataModels.AppointmentData
import org.koin.core.component.KoinComponent

class AppointmentRepository(
    private val database: AppDatabase
) : KoinComponent {
    suspend fun insertAppointment(appointment: AppointmentData) {
        database.appointmentsDao().insertAppointment(appointment)
    }

    fun getAllAppointments(): Flow<List<AppointmentData>> {
        return database.appointmentsDao().getAllAppointments()
    }

    suspend fun clearTable() {
        database.appointmentsDao().clearTable()
    }
}