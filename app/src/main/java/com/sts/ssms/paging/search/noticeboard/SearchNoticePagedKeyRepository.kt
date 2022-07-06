package com.sts.ssms.paging.search.noticeboard

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyRepository
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class SearchNoticePagedKeyRepository(private val noticeRepository: NoticeRepository) :
    PageKeyRepository<Notice>() {

    override fun getSourceFactory(query: String, filterId: Int): ItemDataSourceFactory<Notice> =
        SearchNoticeDataSourceFactory(query, filterId, noticeRepository)

}