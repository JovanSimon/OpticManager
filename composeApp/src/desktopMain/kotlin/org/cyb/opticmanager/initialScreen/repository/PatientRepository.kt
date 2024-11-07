package org.cyb.opticmanager.initialScreen.repository

import kotlinx.coroutines.flow.Flow
import org.cyb.opticmanager.db.AppDatabase
import org.cyb.opticmanager.db.dataModels.PatientData
import org.koin.core.component.KoinComponent

class PatientRepository (
    private val database: AppDatabase
): KoinComponent {
    suspend fun insertPatient(patient: PatientData): Long {
        return database.patientDao().insertPatient(patient)
    }

    fun getAllPatients(): Flow<List<PatientData>> {
        return database.patientDao().getAllPatients()
    }

    suspend fun clearTable() {
        database.patientDao().clearTable()
    }

    suspend fun searchPatients(name: String?, lastname: String?): List<PatientData> {
        return database.patientDao().searchPatients(name, lastname)
    }

    suspend fun getPatientById(id: Long): PatientData {
        return database.patientDao().getPatientById(id)
    }

}