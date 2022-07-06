package com.sts.ssms.paging.societyevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.societyevent.repository.SocietyEventRepository
import com.sts.ssms.ui.helpdesk.societyevent.MY_SOCIETY_EVENT
import com.sts.ssms.ui.helpdesk.societyevent.model.SocietyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SocietyEventDataSource(private val societyEventRepository: SocietyEventRepository) :
    PageKeyedDataSource<Int, SocietyEvent>() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    // Keep reference of the last query (to be able to retry it if necessary)
    private var retryQuery: (() -> Any)? = null

    private val networkState = MutableLiveData<NetworkState>()

    private val _initialLoad = MutableLiveData<NetworkState>()
    private val initialLoad: LiveData<NetworkState>
        get() = _initialLoad

    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, SocietyEvent>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(isInitialLoad = true, page = 1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SocietyEvent>) {
        retryQuery = { loadAfter(params, callback) }
        executeQuery(isInitialLoad = false, page = params.key) {
            callback.onResult(it, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SocietyEvent>) {
    }

    private fun executeQuery(
        isInitialLoad: Boolean,
        page: Int,
        callback: (List<SocietyEvent>) -> Unit
    ) {
        if (isInitialLoad) _initialLoad.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        coroutineScope.launch {
            val response = societyEventRepository.loadSocietyEvent(page)
            if (response.societyEventList != null) {
                callback(response.societyEventList)
                networkState.postValue(NetworkState.LOADED)
                if (isInitialLoad) _initialLoad.postValue(NetworkState.LOADED)
            } else {
                networkState.postValue(NetworkState.error(response.error))
                if (isInitialLoad) _initialLoad.postValue(NetworkState.error(response.error))
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        completableJob.cancel()   // Cancel possible running job to only keep last result searched by user
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun refreshState(): LiveData<NetworkState> = initialLoad

    fun refresh() = invalidate()

    fun selectSocietyEvent(eventType: String) {
        refresh()
    }

    fun retry() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

    // Clear job when the linked activity is destroyed to avoid memory leaks
    fun onCleared() {
        completableJob.cancel()
    }

}