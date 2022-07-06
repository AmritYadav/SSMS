package com.sts.ssms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sts.ssms.data.database.entity.FlatEntity

@Dao
interface FlatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFlats(flatEntities: List<FlatEntity>)

    @Query("SELECT * FROM flat WHERE society_id = :societyId")
    fun getFlatBySociety(societyId: String) : List<FlatEntity>

    // clear all data, when user logs out of app
    @Query("DELETE FROM flat")
    fun clearFlats()
}