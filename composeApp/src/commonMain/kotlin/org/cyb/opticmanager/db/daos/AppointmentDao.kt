package org.cyb.opticmanager.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.cyb.opticmanager.db.dataModels.AppointmentData

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAppointment(appointment: AppointmentData)

    @Query("SELECT * FROM appointments")
    fun getAllAppointments(): Flow<List<AppointmentData>>

    @Query("DELETE FROM appointments")
    suspend fun clearTable()
}