package com.sts.ssms.paging.noticeboard

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class NoticeDataSourceFactory(private val noticeRepository: NoticeRepository) :
    DataSource.Factory<Int, Notice>() {

    val noticeDataSource = MutableLiveData<NoticeDataSource>()

    override fun create(): DataSource<Int, Notice> {
        val dataSource = NoticeDataSource(noticeRepository)
        noticeDataSource.postValue(dataSource)
        return dataSource
    }

    fun getRepository() = noticeRepository

}