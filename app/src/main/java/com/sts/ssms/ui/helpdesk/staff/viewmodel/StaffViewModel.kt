package com.sts.ssms.ui.helpdesk.staff.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.staff.StaffDataSourceFactory
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel

private const val PAGE_SIZE = 6

class StaffViewModel(private val staffDataSourceFactory: StaffDataSourceFactory): ViewModel() {

    var staffList: LiveData<PagedList<StaffUiModel>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

        staffList =
            LivePagedListBuilder<Int, StaffUiModel>(staffDataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(staffDataSourceFactory.staffDataSourceLiveData) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(staffDataSourceFactory.staffDataSourceLiveData) { it.refreshState() }
    }

    fun retry() = staffDataSourceFactory.staffDataSourceLiveData.value?.retry()

    fun refreshAllData() = staffDataSourceFactory.staffDataSourceLiveData.value?.refresh()

    override fun onCleared() {
        super.onCleared()
        staffDataSourceFactory.staffDataSourceLiveData.value?.onCleared()
    }

}