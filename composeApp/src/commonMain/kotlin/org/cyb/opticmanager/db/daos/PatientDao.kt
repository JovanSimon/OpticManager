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

    @Query("SELECT * FROM patients WHERE id = :id")
    suspend fun getPatientById(id: Long): PatientData

    @Query("""
    SELECT * FROM patients 
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%' OR lastname LIKE '%' || :name || '%')
        AND (:lastname IS NULL OR lastname LIKE '%' || :lastname || '%')
    """)
    suspend fun searchPatients(name: String?, lastname: String?): List<PatientData>
}