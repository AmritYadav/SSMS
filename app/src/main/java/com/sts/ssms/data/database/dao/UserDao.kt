package com.sts.ssms.data.database.dao

import androidx.room.*
import com.sts.ssms.data.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLoggedInUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): UserEntity

    @Query("DELETE FROM user")
    fun clearUser()
}