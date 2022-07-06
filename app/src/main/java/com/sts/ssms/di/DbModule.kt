package com.sts.ssms.di

import android.content.Context
import androidx.room.Room
import com.sts.ssms.BuildConfig
import com.sts.ssms.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single { provideRoomDb(androidApplication()) }
}

private fun provideRoomDb(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.SSMS_DB_NAME).build()
}