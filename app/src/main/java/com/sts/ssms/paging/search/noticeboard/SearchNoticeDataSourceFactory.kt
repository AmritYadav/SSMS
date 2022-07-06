package com.sts.ssms.paging.search.noticeboard

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class SearchNoticeDataSourceFactory(
    private val query: String,
    private val noticeId: Int,
    private val noticeRepository: NoticeRepository
) : ItemDataSourceFactory<Notice>() {

    override fun getDataSource(): PageKeyedItemDataSource<Notice> =
        PageKeyedNoticeDataSource(query, noticeId, noticeRepository)
}