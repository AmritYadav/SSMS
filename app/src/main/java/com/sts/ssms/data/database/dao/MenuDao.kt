package com.sts.ssms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sts.ssms.data.database.entity.MenuEntity

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMenu(menuEntity: MenuEntity)

    @Query("SELECT * FROM menu WHERE society_id = :societyId")
    fun getMenuBySociety(societyId: String) : MenuEntity?

    @Query("DELETE FROM menu")
    fun clearMenus()
}