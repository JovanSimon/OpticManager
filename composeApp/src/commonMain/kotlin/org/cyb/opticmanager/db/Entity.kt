package org.cyb.opticmanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test")
data class Entity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val test: String
)