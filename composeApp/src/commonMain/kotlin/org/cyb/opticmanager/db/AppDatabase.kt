package org.cyb.opticmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Entity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun testDao(): TestDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}