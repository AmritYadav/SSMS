package com.sts.ssms.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sts.ssms.BuildConfig
import com.sts.ssms.data.database.dao.*
import com.sts.ssms.data.database.entity.*

@Database(
    entities = [UserEntity::class, SocietyEntity::class, MenuEntity::class, FlatEntity::class, EventEntity::class],
    version = BuildConfig.SSMS_DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getSocietyDao(): SocietyDao
    abstract fun getMenuDao(): MenuDao
    abstract fun getFlatDao(): FlatDao
    abstract fun getEventDao(): EventDao
}