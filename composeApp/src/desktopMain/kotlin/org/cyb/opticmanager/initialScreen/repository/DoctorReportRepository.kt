package org.cyb.opticmanager.initialScreen.repository

import DoctorReport
import kotlinx.coroutines.flow.Flow
import org.cyb.opticmanager.db.AppDatabase

class DoctorReportRepository (
    private val database: AppDatabase
) {
    suspend fun insertDoctorReport(report: DoctorReport) {
        database.doctorReportDao().insertDoctorReport(report)
    }

    fun getAllReports(): Flow<List<DoctorReport>> {
        return database.doctorReportDao().getAll()
    }

    fun getReportsByPatientId(patientId: Long): Flow<List<DoctorReport>> {
        return database.doctorReportDao().getReportsByPatientId(patientId)
    }
}