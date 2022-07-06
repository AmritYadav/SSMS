package com.sts.ssms.ui.helpdesk.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.society.model.Society
import com.sts.ssms.data.society.repository.SocietyRepository
import com.sts.ssms.data.society.repository.SwitchSocietyResult
import kotlinx.coroutines.*

class SocietyViewModel(private val societyRepository: SocietyRepository) : ViewModel() {

    private val completableJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _societies = MutableLiveData<List<Society>>()
    val societyList: LiveData<List<Society>> = _societies

    private var _switchSocietyResult = MutableLiveData<SwitchSocietyResult>()
    val switchSocietyResult: LiveData<SwitchSocietyResult> = _switchSocietyResult

    var societyId: String? = null
        set(value) {
            societyRepository.setSocietyId(value)
            field = value
        }
        get() = societyRepository.getSocietyId()

    fun userSocieties(societyId: String? = null, onSocietySwitch: Boolean) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result =
                societyRepository.loadSocietyFromRemoteLocal(
                    societyId ?: this@SocietyViewModel.societyId
                )
            withContext(Dispatchers.Main) {
                _networkState.value = NetworkState.LOADED
                _societies.value = societyRepository.societyList
                if (onSocietySwitch)
                    _switchSocietyResult.value = result
            }
        }
    }
}