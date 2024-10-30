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
//    val appointmentReports: List<Long>
)

//@Serializable
//data class AppointmentReport(
//    val odSphClose: String,
//    val odClyClose: String,
//    val odAxClose: String,
//    val osSphClose: String,
//    val osClyClose: String,
//    val osAxClose: String,
//    val pdClose: Int,
//    val odSphFar: String,
//    val odClyFar: String,
//    val odAxFar: String,
//    val osSphFar: String,
//    val osClyFar: String,
//    val osAxFar: String,
//    val pdFar: Int,
//    val description: String
//)
//
//class Converters {
//    @TypeConverter
//    fun fromAppointmentReportList(value: List<AppointmentReport>): String {
//        return Json.encodeToString(value)
//    }
//
//    @TypeConverter
//    fun toAppointmentReportList(value: String): List<AppointmentReport> {
//        return Json.decodeFromString(value)
//    }
//}