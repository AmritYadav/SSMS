package com.sts.ssms.ui.helpdesk.noticeboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.noticeboard.NoticeDataSourceFactory
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import com.sts.ssms.ui.helpdesk.noticeboard.models.NoticeTypeResult
import kotlinx.coroutines.*

private const val PAGE_SIZE = 10

class NoticeViewModel(private val noticeDataSourceFactory: NoticeDataSourceFactory) : ViewModel() {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    var noticeList: LiveData<PagedList<Notice>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    private var _noticeTypeResult = MutableLiveData<NoticeTypeResult>()
    val noticeTypeResult: LiveData<NoticeTypeResult> = _noticeTypeResult

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

        noticeList =
            LivePagedListBuilder<Int, Notice>(noticeDataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(noticeDataSourceFactory.noticeDataSource) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(noticeDataSourceFactory.noticeDataSource) { it.refreshState() }

    }

    fun retry() = noticeDataSourceFactory.noticeDataSource.value?.retry()

    fun refreshAllData() = noticeDataSourceFactory.noticeDataSource.value?.refresh()

    fun loadNotice(typeId: Int) = noticeDataSourceFactory.noticeDataSource.value?.loadNoticeType(typeId)

    fun getNoticeType(): Int = noticeDataSourceFactory.getRepository().getNoticeType()

    fun loadNoticeTypes() {
        coroutineScope.launch {
            val result = noticeDataSourceFactory.getRepository().societyNoticeTypes()
            withContext(Dispatchers.Main) {
                _noticeTypeResult.value = result
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        noticeDataSourceFactory.noticeDataSource.value?.onCleared()
        job.cancel()
    }

}