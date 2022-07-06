package com.sts.ssms.ui.helpdesk.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket
import com.sts.ssms.data.helpdesk.dashboard.repository.DashboardRepository
import kotlinx.coroutines.*

class DashboardViewModel(private val dashboardRepository: DashboardRepository) : ViewModel() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _tickets = MutableLiveData<List<Ticket>>()
    val ticketList: LiveData<List<Ticket>> = _tickets

    private var _filterAction = MutableLiveData<Int>().apply {
        R.id.action_all
    }
    val filterAction: LiveData<Int> = _filterAction

    fun setFilterAction(actionId: Int) {
        _filterAction.value = actionId
    }

    fun getActiveFilterAction(): Int = _filterAction.value ?: R.id.action_all

    fun loadDashboardTickets() {
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                _networkState.value = NetworkState.LOADING
            }
            val result = dashboardRepository.ticketsList()
            withContext(Dispatchers.Main) {
                if (result.ticketsList != null) {
                    _networkState.value = NetworkState.LOADED
                    _tickets.value = result.ticketsList
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        completableJob.cancel()
    }
}