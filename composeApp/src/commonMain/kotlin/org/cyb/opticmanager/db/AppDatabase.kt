package org.cyb.opticmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
import org.cyb.opticmanager.db.daos.AppointmentDao
import org.cyb.opticmanager.db.daos.PatientDao
import org.cyb.opticmanager.db.dataModels.AppointmentData
import org.cyb.opticmanager.db.dataModels.PatientData

//import org.cyb.opticmanager.db.dataModels.Converters

@Database(
    entities = [
        AppointmentData::class,
        PatientData::class
    ],
    version = 3,
    exportSchema = true
)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun appointmentsDao(): AppointmentDao
    abstract fun patientDao(): PatientDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}