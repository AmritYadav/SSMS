package com.sts.ssms.paging.search.documents

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRepository
import com.sts.ssms.ui.helpdesk.documents.model.Documents

class SearchDocumentsDataSourceFactory(
    private val query: String,
    private val flatId: Int,
    private val documentsRepository: DocumentsRepository
) : ItemDataSourceFactory<Documents>() {

    override fun getDataSource(): PageKeyedItemDataSource<Documents> =
        PageKeyedDocumentsDataSource(query, flatId, documentsRepository)
}