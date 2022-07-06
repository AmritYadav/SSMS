package com.sts.ssms.data.login.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class AuthTokenLocalDataSource(private val prefs: SharedPreferences) {

    private var _authToken: String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    /**
     * Access token used for requests that require authentication
     * at every API calls after successful login
     */
    var authToken: String? = _authToken
        set(value) {
            prefs.edit { putString(KEY_ACCESS_TOKEN, value) }
            field = value
        }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}