package com.sts.ssms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sts.ssms.data.database.entity.EventEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveEvents(eventEntities: List<EventEntity>)

    @Query("SELECT * FROM event")
    fun allEvents(): List<EventEntity>

    @Query("DELETE FROM event")
    fun clearEvents()
}