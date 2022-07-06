package com.sts.ssms.paging.search.documents

import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRepository
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class PageKeyedDocumentsDataSource(
    private val query: String,
    private val flatId: Int,
    private val documentsRepository: DocumentsRepository
): PageKeyedItemDataSource<Documents>() {
    override suspend fun fetchItem(page: Int): SearchResult<Documents> =
        documentsRepository.getItem(page, query, flatId)

    override fun getSearchRepository(): SearchRepository<Documents> = documentsRepository
}