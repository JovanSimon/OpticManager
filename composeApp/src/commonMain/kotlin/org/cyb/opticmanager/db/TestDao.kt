package org.cyb.opticmanager.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Query("SELECT * FROM test")
    fun getAll(): Flow<List<Entity>>
}