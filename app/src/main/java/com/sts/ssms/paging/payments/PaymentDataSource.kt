package com.sts.ssms.paging.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.payments.repository.PaymentRepository
import com.sts.ssms.paging.payments.model.BillDownloadResult
import com.sts.ssms.ui.helpdesk.paymentdetails.model.PaymentUiModel
import kotlinx.coroutines.*

class PaymentDataSource(private val paymentRepository: PaymentRepository) :
    PageKeyedDataSource<Int, PaymentUiModel>() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    // Keep reference of the last query (to be able to retry it if necessary)
    private var retryQuery: (() -> Any)? = null

    private val networkState = MutableLiveData<NetworkState>()

    private val _initialLoad = MutableLiveData<NetworkState>()
    private val initialLoad: LiveData<NetworkState>
        get() = _initialLoad

    private val _downloadBillResult = MutableLiveData<BillDownloadResult>()
    val downloadBillResult: LiveData<BillDownloadResult> = _downloadBillResult

    private val _downloadStatus = MutableLiveData<NetworkState>()
    private val downloadState: LiveData<NetworkState>
        get() = _downloadStatus

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PaymentUiModel>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(isInitialLoad = true, page = 1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PaymentUiModel>) {
        retryQuery = { loadAfter(params, callback) }
        executeQuery(isInitialLoad = false, page = params.key) {
            callback.onResult(it, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PaymentUiModel>) {
    }

    private fun executeQuery(
        isInitialLoad: Boolean,
        page: Int,
        callback: (List<PaymentUiModel>) -> Unit
    ) {
        if (isInitialLoad) _initialLoad.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        coroutineScope.launch {
            val response = paymentRepository.fetchPayments(page)
            if (response.paymentList != null) {
                callback(response.paymentList)
                if (isInitialLoad) _initialLoad.postValue(NetworkState.LOADED)
                networkState.postValue(NetworkState.LOADED)
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

    fun downloadState(): LiveData<NetworkState> = downloadState

    fun refresh() = invalidate()

    fun retry() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

    fun downloadBill(paymentId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { _downloadStatus.value = NetworkState.LOADING }
            val billDownloadResult = paymentRepository.billDownload(paymentId)
            withContext(Dispatchers.Main) { _downloadBillResult.value = billDownloadResult }
            withContext(Dispatchers.Main) { _downloadStatus.value = NetworkState.LOADED }
        }
    }

    // Clear job when the linked activity is destroyed to avoid memory leaks
    fun onCleared() {
        completableJob.cancel()
    }
}