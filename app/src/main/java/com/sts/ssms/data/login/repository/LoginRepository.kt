package com.sts.ssms.data.login.repository

import com.sts.ssms.data.login.repository.local.LoginLocalDataSource
import com.sts.ssms.data.login.repository.remote.LoginRemoteDataSource
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.database.entity.UserEntity
import com.sts.ssms.data.login.model.LoggedInUser
import com.sts.ssms.data.login.model.LoginApiResult

class LoginRepository(
    private val loginLocalDataSource: LoginLocalDataSource,
    private val loginRemoteDataSource: LoginRemoteDataSource
) {

    // local cache of the user object, so we don't retrieve it from the local storage every time
    // we need it
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    private var onUserLogoutClearDb: (() -> Boolean) = {
        loginLocalDataSource.logout()
    }

    init {
        user = loginLocalDataSource.getLoggedInUser()
    }

    suspend fun login(email: String, password: String): LoginApiResult =
        when (val result = loginRemoteDataSource.login(email, password)) {
            is Result.Success -> {
                loginLocalDataSource.setLoggedInUser(result.data)
                setLoggedInUser(loginLocalDataSource.getLoggedInUser())
                LoginApiResult(loggedInUser = loginLocalDataSource.getLoggedInUser())
            }
            is Result.Error -> {
                loginLocalDataSource.clearUserPrefs()
                LoginApiResult(error = result.exception.message)
            }
        }

    fun updateUser(userEntity: UserEntity) = loginLocalDataSource.updateUser(userEntity)

    fun getUserProfile() = loginLocalDataSource.getUserProfile()

    fun logout(): Boolean {
        user = null
        loginRemoteDataSource.logout()
        return onUserLogoutClearDb.invoke()
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser?) {
        this.user = loggedInUser
    }
}