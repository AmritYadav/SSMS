package com.sts.ssms.data.login.repository.local

import com.sts.ssms.data.database.entity.LoginResponse
import com.sts.ssms.data.database.entity.UserEntity
import com.sts.ssms.data.login.model.LoggedInUser

interface LoginLocalDataSource {
    fun setLoggedInUser(loginResponse: LoginResponse)
    fun updateUser(userEntity: UserEntity)
    fun getLoggedInUser(): LoggedInUser?
    fun clearUserPrefs()
    fun getUserProfile(): UserEntity
    fun logout() : Boolean
}