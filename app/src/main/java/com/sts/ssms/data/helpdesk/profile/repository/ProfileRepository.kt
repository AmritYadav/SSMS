package com.sts.ssms.data.helpdesk.profile.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.profile.api.toProfile
import com.sts.ssms.data.login.repository.LoginRepository
import com.sts.ssms.ui.helpdesk.profile.model.UserProfileUI

class ProfileRepository(
    private val loginRepository: LoginRepository,
    private val profileRemoteDataSource: ProfileRemoteDataSource
) {

    suspend fun loadProfile(): UserProfileUI =
        when (val response = profileRemoteDataSource.userProfile()) {
            is Result.Success -> {
                loginRepository.updateUser(response.data)
                UserProfileUI(profile = response.data.toProfile())
            }
            is Result.Error -> {
                // load user details from the cache
                val userEntity = loginRepository.getUserProfile()
                UserProfileUI(profile = userEntity.toProfile())
            }
        }

    fun logoutOnPasswordChange() {
        loginRepository.logout()
    }
}