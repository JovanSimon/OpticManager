package org.cyb.opticmanager.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.cyb.opticmanager.db.dataModels.PatientData

//import org.cyb.opticmanager.db.dataModels.Patient

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPatient(patient: PatientData)

    @Query("SELECT * FROM patients")
    fun getAllPatients(): Flow<List<PatientData>>

    @Query("DELETE FROM patients")
    suspend fun clearTable()

    @Query("SELECT * FROM patients WHERE name = :name AND lastname = :lastname")
    suspend fun getPatientWithNameAndLastname(name: String, lastname: String): List<PatientData>
}