package com.sts.ssms.data.helpdesk.notice.api

import com.sts.ssms.data.common.CommonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeBoardApi {

    @GET("/v3/notice/getList")
    suspend fun notices(
        @Query("page") pageIndex: Int, @Query("per_page") perPage: Int,
        @Query("type") type: Int, @Query("search") query: String
    ): Response<CommonApiResponse<NoticeApiResponse>>

    @GET("/v3/getListType/notice")
    suspend fun noticeTypes(): Response<CommonApiResponse<List<NoticeTypeResponse>>>

}