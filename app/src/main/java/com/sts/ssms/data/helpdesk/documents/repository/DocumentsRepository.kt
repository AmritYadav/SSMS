package com.sts.ssms.data.helpdesk.documents.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.documents.api.toDocument
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import com.sts.ssms.ui.helpdesk.documents.model.DocumentsResult

class DocumentsRepository(private val remoteDataSource: DocumentsRemoteDataSource) :
    SearchRepository<Documents> {

    private var flatId: Int = 0
    fun setFlatId(id: Int) {
        flatId = id
    }

    suspend fun allDocument(pageIndex: Int): DocumentsResult {
        return when (val result = remoteDataSource.documents(flatId, pageIndex)) {
            is Result.Success -> DocumentsResult(documentList = result.data.documents.map { it.toDocument() })
            is Result.Error -> DocumentsResult(error = result.exception.message)
        }
    }

    override suspend fun getItem(page: Int, query: String, filterId: Int): SearchResult<Documents> {
        return when (val result = remoteDataSource.documents(filterId, page, query, 10)) {
            is Result.Success -> SearchResult(itemList = result.data.documents.map { it.toDocument() })
            is Result.Error -> SearchResult(error = result.exception.message)
        }
    }
}