package com.sts.ssms.paging.search.documents

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyRepository
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRepository
import com.sts.ssms.data.helpdesk.notice.repository.NoticeRepository
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class SearchDocumentsPagedKeyRepository(private val documentsRepository: DocumentsRepository) :
    PageKeyRepository<Documents>() {

    override fun getSourceFactory(query: String, filterId: Int): ItemDataSourceFactory<Documents> =
        SearchDocumentsDataSourceFactory(query, filterId, documentsRepository)

}