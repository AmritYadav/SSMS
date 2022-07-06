package com.sts.ssms.ui.helpdesk.vendor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.vendor.VendorDataSourceFactory
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

private const val PAGE_SIZE = 6

class VendorViewModel(private val vendorDataSourceFactory: VendorDataSourceFactory) :
    ViewModel() {

    var vendorList: LiveData<PagedList<VendorUiModel>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

        vendorList =
            LivePagedListBuilder<Int, VendorUiModel>(vendorDataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(vendorDataSourceFactory.vendorDataSource) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(vendorDataSourceFactory.vendorDataSource) { it.refreshState() }
    }

    fun retry() = vendorDataSourceFactory.vendorDataSource.value?.retry()

    fun refreshAllData() = vendorDataSourceFactory.vendorDataSource.value?.refresh()

    override fun onCleared() {
        vendorDataSourceFactory.vendorDataSource.value?.onCleared()
        super.onCleared()
    }
}
