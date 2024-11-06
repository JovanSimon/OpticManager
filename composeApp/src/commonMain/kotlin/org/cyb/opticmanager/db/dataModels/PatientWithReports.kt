package org.cyb.opticmanager.db.dataModels

import DoctorReport
import androidx.room.Embedded
import androidx.room.Relation

data class PatientWithReports (
    @Embedded val patient: PatientData,
    @Relation(
        parentColumn = "id",
        entityColumn = "patientId"
    )
    val reports: List<DoctorReport>
)