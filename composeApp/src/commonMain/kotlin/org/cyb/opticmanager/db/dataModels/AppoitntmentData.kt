package org.cyb.opticmanager.db.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class AppointmentData (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val lastName: String,
    val time: String,
    val description: String
)