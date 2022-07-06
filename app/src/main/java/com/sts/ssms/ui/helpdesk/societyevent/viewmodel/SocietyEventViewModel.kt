package com.sts.ssms.ui.helpdesk.societyevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.societyevent.SocietyEventDataSourceFactory
import com.sts.ssms.ui.helpdesk.societyevent.model.SocietyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

private const val PAGE_SIZE = 10
class SocietyEventViewModel(private val societyEventDataSourceFactory: SocietyEventDataSourceFactory) :
    ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    var societyEventList: LiveData<PagedList<SocietyEvent>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

        societyEventList =
            LivePagedListBuilder<Int, SocietyEvent>(societyEventDataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(societyEventDataSourceFactory.societyEventDataSource) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(societyEventDataSourceFactory.societyEventDataSource) { it.refreshState() }

    }

    fun retry() = societyEventDataSourceFactory.societyEventDataSource.value?.retry()

    fun refreshAllData() = societyEventDataSourceFactory.societyEventDataSource.value?.refresh()

    fun loadNotice(eventType: String) = societyEventDataSourceFactory.societyEventDataSource.value?.selectSocietyEvent(eventType)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}