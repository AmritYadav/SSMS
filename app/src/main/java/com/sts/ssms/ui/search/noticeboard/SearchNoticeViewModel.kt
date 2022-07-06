package com.sts.ssms.ui.search.noticeboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.paging.search.noticeboard.SearchNoticePagedKeyRepository
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class SearchNoticeViewModel(val noticeRepository: NoticeRepository) :
    SearchViewModel<Notice>() {

    override val repoResult: LiveData<Listing<Notice>> = Transformations.map(query) {
        SearchNoticePagedKeyRepository(noticeRepository).getItems(it, filterId)
    }

}