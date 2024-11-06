package org.cyb.opticmanager.db.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class PatientData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val lastname: String,
    val dateOfBirth: String,
    val placeOfLiving: String,
)