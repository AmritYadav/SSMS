package com.sts.ssms.ui.helpdesk.amenity.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.amenity.allrequest.AllRequestAmenityDataSourceFactory
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest

private const val PAGE_SIZE = 10

class AmenityAllRequestViewModel(private val dataSourceFactory: AllRequestAmenityDataSourceFactory) :
    ViewModel() {

    var allAmenityRequests: LiveData<PagedList<AllAmenityRequest>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
        allAmenityRequests =
            LivePagedListBuilder<Int, AllAmenityRequest>(dataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(dataSourceFactory.dataSourceLiveData) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(dataSourceFactory.dataSourceLiveData) { it.refreshState() }
    }

    fun refreshAllData() = dataSourceFactory.dataSourceLiveData.value?.refresh()

    fun retry() = dataSourceFactory.dataSourceLiveData.value?.retry()

    override fun onCleared() {
        super.onCleared()
        dataSourceFactory.dataSourceLiveData.value?.onCleared()
    }
}