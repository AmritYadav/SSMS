package com.sts.ssms.data.database.dao

import androidx.room.*
import com.sts.ssms.data.database.entity.SocietyEntity

@Dao
interface SocietyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLoggedInUserSocieties(societies: List<SocietyEntity>)

    @Query("SELECT * FROM society")
    fun societyList() : List<SocietyEntity>

    @Query("DELETE FROM society")
    fun clearSocieties()
}