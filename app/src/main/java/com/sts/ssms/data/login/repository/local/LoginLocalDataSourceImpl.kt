package com.sts.ssms.data.login.repository.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sts.ssms.data.database.AppDatabase
import com.sts.ssms.data.database.entity.LoginResponse
import com.sts.ssms.data.database.entity.UserEntity
import com.sts.ssms.data.login.model.LoggedInUser
import com.sts.ssms.data.society.repository.SocietyRepository
import com.sts.ssms.data.society.repository.SocietyResult
import kotlinx.coroutines.*

class LoginLocalDataSourceImpl(
    private val societyRepository: SocietyRepository,
    private val prefs: SharedPreferences,
    private val appDatabase: AppDatabase
) : LoginLocalDataSource {

    companion object {
        const val LOGIN_PREFERENCE = "LOGIN_PREFERENCE"
        const val KEY_USER_ID = "USER_ID"
        private const val KEY_USER_EMAIL = "USER_EMAIL"
        const val KEY_USER_AVATAR = "USER_AVATAR"
        const val KEY_USER_NAME = "USER_NAME"
        private const val KEY_PASSWORD_CHANGED = "PASSWORD_CHANGED"
    }

    override fun setLoggedInUser(loginResponse: LoginResponse) {
        if (loginResponse.userEntity != null) saveUserPrefs(
            loginResponse.userEntity,
            loginResponse.passwordChanged
        )
        societyRepository.transformSetMenuEntityToMenu(loginResponse.menuEntity)
        if (!loginResponse.societyEntityList.isNullOrEmpty()) {
            societyRepository.setSocietyId(societyId = loginResponse.societyEntityList[0].societyId)
        }
        insertUserAndSociety(loginResponse)
    }

    override fun updateUser(userEntity: UserEntity) {
        updateUserPrefs(userEntity)
        appDatabase.getUserDao().updateUser(userEntity)
    }

    override fun getLoggedInUser(): LoggedInUser? {
        val userId = prefs.getString(KEY_USER_ID, null)
        val userEmail = prefs.getString(KEY_USER_EMAIL, null)
        val displayName = prefs.getString(KEY_USER_NAME, null)
        val userAvatar = prefs.getString(KEY_USER_AVATAR, null)
        val passwordChanged = prefs.getInt(KEY_PASSWORD_CHANGED, -1)
        if (userId == null)
            return null
        return LoggedInUser(userId, userEmail!!, displayName!!, userAvatar, passwordChanged)
    }

    override fun getUserProfile(): UserEntity {
        return appDatabase.getUserDao().getUser()
    }

    override fun logout(): Boolean = runBlocking {
        resetUserPreference()
        withContext(Dispatchers.Default) { clearUserFromDb() }
        return@runBlocking true
    }

    override fun clearUserPrefs() {
        prefs.edit { clear() }
    }

    private fun saveUserPrefs(userEntity: UserEntity, passwordChanged: Int) {
        val displayName = "${userEntity.firstName} ${userEntity.lastName}"
        prefs.edit {
            putString(KEY_USER_ID, userEntity.userId)
            putString(KEY_USER_EMAIL, userEntity.email)
            putString(KEY_USER_NAME, displayName)
            putString(KEY_USER_AVATAR, userEntity.imageUrl)
            putInt(KEY_PASSWORD_CHANGED, passwordChanged)
        }
    }

    private fun updateUserPrefs(userEntity: UserEntity) {
        val displayName = "${userEntity.firstName} ${userEntity.lastName}"
        prefs.edit {
            putString(KEY_USER_NAME, displayName)
            putString(KEY_USER_AVATAR, userEntity.imageUrl)
        }
    }

    private fun insertUserAndSociety(loginResponse: LoginResponse) {
        loginResponse.userEntity?.let {
            appDatabase.getUserDao().saveLoggedInUser(it)
        }
        val userSocietyEntity = SocietyResult(
            loginResponse.societyEntityList,
            loginResponse.flatEntityList,
            loginResponse.menuEntity
        )
        societyRepository.saveSocieties(userSocietyEntity)
    }

    private fun clearUserFromDb() {
        appDatabase.getUserDao().clearUser()
        societyRepository.clearSocieties()
    }

    private fun resetUserPreference() {
        prefs.edit {
            putString(KEY_USER_ID, null)
            putString(KEY_USER_EMAIL, null)
            putString(KEY_USER_NAME, null)
            putString(KEY_USER_AVATAR, null)
            putInt(KEY_PASSWORD_CHANGED, -1)
        }
    }
}