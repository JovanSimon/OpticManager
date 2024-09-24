package org.cyb.opticmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.cyb.opticmanager.db.daos.AppointmentDao
import org.cyb.opticmanager.db.dataModels.AppointmentData

@Database(
    entities = [
        AppointmentData::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun appointmentsDao(): AppointmentDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}