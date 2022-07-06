package com.sts.ssms.data.helpdesk.notice.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.notice.api.toNotice
import com.sts.ssms.data.helpdesk.notice.api.toNoticeType
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import com.sts.ssms.ui.helpdesk.noticeboard.models.NoticeResult
import com.sts.ssms.ui.helpdesk.noticeboard.models.NoticeType
import com.sts.ssms.ui.helpdesk.noticeboard.models.NoticeTypeResult

class NoticeRepository(private val remoteDataSource: NoticeRemoteDataSource) :
    SearchRepository<Notice> {

    private var noticeType: Int = 0

    fun setNoticeType(id: Int) {
        noticeType = id
    }

    fun getNoticeType(): Int {
        return noticeType
    }

    suspend fun societyNotices(page: Int): NoticeResult {
        return when (val result = remoteDataSource.notices(page, noticeType)) {
            is Result.Success -> NoticeResult(noticeList = result.data.notices.map { it.toNotice() })
            is Result.Error -> NoticeResult(error = result.exception.message)
        }
    }

    suspend fun societyNoticeTypes(): NoticeTypeResult {
        return when (val result = remoteDataSource.noticeTypes()) {
            is Result.Success -> loadNoticeTypesWithDefaultType(result.data.map { it.toNoticeType() })
            is Result.Error -> NoticeTypeResult(error = result.exception.message)
        }
    }

    private fun loadNoticeTypesWithDefaultType(noticeTypes: List<NoticeType>): NoticeTypeResult {
        val types = mutableListOf(NoticeType(0, "All"))
        if (noticeTypes.isNotEmpty()) types.addAll(noticeTypes)
        return NoticeTypeResult(noticeTypes = types)
    }

    override suspend fun getItem(page: Int, query: String, filterId: Int): SearchResult<Notice> {
        return when (val result = remoteDataSource.notices(page, filterId, query)) {
            is Result.Success -> SearchResult(itemList = result.data.notices.map { it.toNotice() })
            is Result.Error -> SearchResult(error = result.exception.message)
        }
    }
}