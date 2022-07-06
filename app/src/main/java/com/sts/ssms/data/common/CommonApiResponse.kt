package com.sts.ssms.data.common

import com.google.gson.annotations.SerializedName

data class CommonApiResponse<T>(
    @SerializedName("status_code")
    val statusCode: Int,
    val response: T?,
    @SerializedName("fault")
    val errorBody: List<String>?
) : SsmsApi {
    override fun isSuccess(): Boolean = statusCode == 200
    override fun errorMessage(): String? = errorBody?.joinToString(separator = ".")
}

interface SsmsApi {
    fun isSuccess(): Boolean
    fun errorMessage(): String?
}