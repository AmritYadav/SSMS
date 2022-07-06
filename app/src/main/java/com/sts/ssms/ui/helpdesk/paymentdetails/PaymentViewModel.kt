package com.sts.ssms.ui.helpdesk.paymentdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.payments.PaymentDataSourceFactory
import com.sts.ssms.paging.payments.model.BillDownloadResult
import com.sts.ssms.ui.helpdesk.paymentdetails.model.PaymentUiModel

private const val PAGE_SIZE = 10

class PaymentViewModel(private val dataSourceFactory: PaymentDataSourceFactory) : ViewModel() {

    var paymentList: LiveData<PagedList<PaymentUiModel>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null
    var downloadState: LiveData<NetworkState>? = null
    var billResult: LiveData<BillDownloadResult>? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

        paymentList = LivePagedListBuilder<Int, PaymentUiModel>(dataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(dataSourceFactory.paymentDataSourceLiveData) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(dataSourceFactory.paymentDataSourceLiveData) { it.refreshState() }
        downloadState =
            Transformations.switchMap(dataSourceFactory.paymentDataSourceLiveData) { it.downloadState() }
        billResult =
            Transformations.switchMap(dataSourceFactory.paymentDataSourceLiveData) { it.downloadBillResult }
    }

    fun retry() = dataSourceFactory.paymentDataSourceLiveData.value?.retry()

    fun downloadBill(paymentId: Int) = dataSourceFactory.paymentDataSourceLiveData.value?.downloadBill(paymentId)

    override fun onCleared() {
        dataSourceFactory.paymentDataSourceLiveData.value?.onCleared()
        super.onCleared()
    }

}