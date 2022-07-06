package com.sts.ssms.data.helpdesk.bylaws.repository

import com.google.gson.JsonParser
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.bylaws.api.ByLawsApi
import com.sts.ssms.ui.helpdesk.bylaws.model.ByLawFormDownloadResult
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class ByLawsRemoteDataSource(private val byLawsApi: ByLawsApi) {

    suspend fun bylawsForm(formNumber: Int) = safeApiCall(
        call = { requestByLawsForm(formNumber) },
        errorMessage = "Could Not able to download form"
    )

    private suspend fun requestByLawsForm(formNumber: Int): Result<ByLawFormDownloadResult> {
        val response = byLawsApi.downloadPdfByLawForm(formNumber)
        response.contentType()?.let {
            if (it.subtype == "json") {
                val parser = JsonParser()
                val errorResponse = parser.parse(response.string()).asJsonObject
                val errorMsg = errorResponse.getAsJsonArray("fault").joinToString(separator = ".")
                return Result.Error(IOException(errorMsg))
            }
        }
        return Result.Success(ByLawFormDownloadResult(success = response.byteStream()))
    }
}