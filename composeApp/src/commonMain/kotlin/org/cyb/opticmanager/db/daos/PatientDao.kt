package org.cyb.opticmanager.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.cyb.opticmanager.db.dataModels.PatientData
import org.cyb.opticmanager.db.dataModels.PatientWithReports

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPatient(patient: PatientData): Long

    @Query("SELECT * FROM patients")
    fun getAllPatients(): Flow<List<PatientData>>

    @Query("DELETE FROM patients")
    suspend fun clearTable()

    @Query("SELECT * FROM patients WHERE name = :name AND lastname = :lastname")
    suspend fun getPatientWithNameAndLastname(name: String, lastname: String): List<PatientData>

//    @Transaction
//    @Query("SELECT * FROM patients WHERE id = :patientId")
//    suspend fun getPatientWithReports(patient: Long): PatientWithReports
}