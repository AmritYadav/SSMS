package com.sts.ssms.paging.search.noticeboard

import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class PageKeyedNoticeDataSource(
    private val query: String,
    private val noticeId: Int,
    private val noticeRepository: NoticeRepository
): PageKeyedItemDataSource<Notice>() {
    override suspend fun fetchItem(page: Int): SearchResult<Notice> =
        noticeRepository.getItem(page, query, noticeId)

    override fun getSearchRepository(): SearchRepository<Notice> = noticeRepository
}