package com.sts.ssms.ui.helpdesk.officialcommunication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.communication.repository.CommRepository
import com.sts.ssms.ui.helpdesk.officialcommunication.models.CommunicationsResult
import kotlinx.coroutines.*

class CommunicationViewModel(private val repository: CommRepository) : ViewModel() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _communicationResult = MutableLiveData<CommunicationsResult>()
    val communicationsResult: LiveData<CommunicationsResult> = _communicationResult

    fun loadCommunications() {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = repository.communicationsList()
            withContext(Dispatchers.Main) {
                if (result.communications != null) {
                    _communicationResult.value = result
                    _networkState.value = NetworkState.LOADED
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }
}