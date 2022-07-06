package com.sts.ssms.data.helpdesk.notice.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.notice.api.NoticeApiResponse
import com.sts.ssms.data.helpdesk.notice.api.NoticeBoardApi
import com.sts.ssms.data.helpdesk.notice.api.NoticeTypeResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class NoticeRemoteDataSource(private val noticeBoardApi: NoticeBoardApi) {

    suspend fun notices(page: Int, type: Int = 0, query: String = ""): Result<NoticeApiResponse> =
        safeApiCall(
            call = { requestNotices(page, type, query) },
            errorMessage = "Error Loading notices"
        )

    suspend fun noticeTypes(): Result<List<NoticeTypeResponse>> =
        safeApiCall(
            call = { requestNoticeTypes() },
            errorMessage = "Error Loading notices"
        )


    private suspend fun requestNotices(
        page: Int, type: Int = 0, query: String = ""
    ): Result<NoticeApiResponse> {
        val apiResponse = noticeBoardApi.notices(page, 20, type, query)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestNoticeTypes(): Result<List<NoticeTypeResponse>> {
        val apiResponse = noticeBoardApi.noticeTypes()
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }
}