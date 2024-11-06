import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.cyb.opticmanager.db.dataModels.PatientData

@Entity(
    tableName = "doctor_reports",
    foreignKeys = [ForeignKey(
        entity = PatientData::class,
        parentColumns = ["id"],
        childColumns = ["patientId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("patientId")]
)
data class DoctorReport(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val patientId: Long,
    val odSphFar: String,
    val odClyFar: String,
    val odAxFar: String,
    val osSphFar: String,
    val osClyFar: String,
    val osAxFar: String,
    val pdFar: Int,
    val odSphClose: String,
    val odClyClose: String,
    val odAxClose: String,
    val osSphClose: String,
    val osClyClose: String,
    val osAxClose: String,
    val pdClose: Int,
    val description: String,
)