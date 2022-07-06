package com.sts.ssms.data.helpdesk.bylaws.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.ui.helpdesk.bylaws.model.ByLawFormDownloadResult

class ByLawsRepository(private val byLawsRemoteDataSource: ByLawsRemoteDataSource) {

    suspend fun getDocument(documentId: Int): ByLawFormDownloadResult {
        return when (val result = byLawsRemoteDataSource.bylawsForm(documentId)) {
            is Result.Success -> result.data
            is Result.Error -> ByLawFormDownloadResult(error = result.exception.message)
        }
    }

}