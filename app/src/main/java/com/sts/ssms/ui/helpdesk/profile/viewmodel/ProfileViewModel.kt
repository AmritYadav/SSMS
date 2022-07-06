package com.sts.ssms.ui.helpdesk.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.data.common.LoadingState
import com.sts.ssms.data.helpdesk.profile.repository.ProfileRepository
import com.sts.ssms.ui.helpdesk.profile.model.Profile
import kotlinx.coroutines.*

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    private var _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile

    private var _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> = _loading

    val passwordStatus = MutableLiveData<Boolean>()

    fun loadProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { _loading.value = LoadingState.LOADING }
            val response = profileRepository.loadProfile()
            delay(200)
            if (response.profile != null) {
                withContext(Dispatchers.Main) {
                    _profile.value = response.profile
                    _loading.value = LoadingState.LOADED
                }
            } else {
                withContext(Dispatchers.Main) { _loading.value = LoadingState.ERROR }
            }
        }
    }

    fun logout() {
        profileRepository.logoutOnPasswordChange()
    }

    override fun onCleared() {
        super.onCleared()
        passwordStatus.value = false
    }
}
