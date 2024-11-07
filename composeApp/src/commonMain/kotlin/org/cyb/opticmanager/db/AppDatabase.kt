package org.cyb.opticmanager.db

import DoctorReport
import androidx.room.Database
import androidx.room.RoomDatabase
import org.cyb.opticmanager.db.daos.AppointmentDao
import org.cyb.opticmanager.db.daos.DoctorReportDao
import org.cyb.opticmanager.db.daos.PatientDao
import org.cyb.opticmanager.db.dataModels.AppointmentData
import org.cyb.opticmanager.db.dataModels.PatientData


@Database(
    entities = [
        AppointmentData::class,
        PatientData::class,
        DoctorReport::class
    ],
    version = 9,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun appointmentsDao(): AppointmentDao
    abstract fun patientDao(): PatientDao
    abstract fun doctorReportDao(): DoctorReportDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}