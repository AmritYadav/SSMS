package com.sts.ssms.data.helpdesk.documents.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.documents.api.DocumentsApi
import com.sts.ssms.data.helpdesk.documents.api.DocumentsApiResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class DocumentsRemoteDataSource(private val documentsApi: DocumentsApi) {

    suspend fun documents(
        flatId: Int, pageIndex: Int, searchQuery: String = "", perPage: Int = 20
    ) = safeApiCall(
        call = { requestDocuments(flatId, pageIndex, perPage, searchQuery) },
        errorMessage = "Error Loading Documents"
    )

    private suspend fun requestDocuments(
        flatId: Int, pageIndex: Int, perPage: Int = 20, searchQuery: String = ""
    ): Result<DocumentsApiResponse> {
        val apiResponse = documentsApi.documents(flatId, pageIndex, perPage, searchQuery)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }
}