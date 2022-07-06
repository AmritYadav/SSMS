package com.sts.ssms.data.society.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sts.ssms.data.database.AppDatabase
import com.sts.ssms.data.database.entity.FlatEntity
import com.sts.ssms.data.database.entity.MenuEntity
import com.sts.ssms.data.database.entity.SocietyEntity
import com.sts.ssms.data.login.repository.local.LoginLocalDataSourceImpl.Companion.KEY_USER_ID

class SocietyLocalDataSource(
    private val prefs: SharedPreferences,
    private val appDatabase: AppDatabase
) {

    var societyId: String? = null
        set(value) {
            prefs.edit {
                putString(KEY_SELECTED_SOCIETY_ID, value)
            }
            field = value
        }
        get() = prefs.getString(KEY_SELECTED_SOCIETY_ID, null)

    val userId: String?
        get() = prefs.getString(KEY_USER_ID, null)

    fun saveSocieties(societyResult: SocietyResult) {
        societyResult.societyList?.let {
            appDatabase.getSocietyDao().saveLoggedInUserSocieties(it)
        }
        societyResult.flatList?.let { appDatabase.getFlatDao().saveFlats(it) }
        societyResult.menu?.let { appDatabase.getMenuDao().saveMenu(it) }
    }

    fun getSociety(): List<SocietyEntity> {
        return appDatabase.getSocietyDao().societyList()
    }

    fun getFlats(societyId: String): List<FlatEntity> {
        return appDatabase.getFlatDao().getFlatBySociety(societyId)
    }

    fun getMenuBySociety(): MenuEntity? {
        societyId?.let {
            return appDatabase.getMenuDao().getMenuBySociety(it)
        }
        return null
    }

    fun clearSocietyEntries() {
        appDatabase.getSocietyDao().clearSocieties()
        appDatabase.getMenuDao().clearMenus()
        appDatabase.getFlatDao().clearFlats()
    }

    companion object {
        const val KEY_SELECTED_SOCIETY_ID = "SELECTED_SOCIETY_ID"
    }
}