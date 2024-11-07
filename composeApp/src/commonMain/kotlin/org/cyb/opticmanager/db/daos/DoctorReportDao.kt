package org.cyb.opticmanager.db.daos

import DoctorReport
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctorReport(report: DoctorReport)

    @Query("SELECT * FROM doctor_reports")
    fun getAll(): Flow<List<DoctorReport>>
}