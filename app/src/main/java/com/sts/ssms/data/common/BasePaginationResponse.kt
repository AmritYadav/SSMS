package com.sts.ssms.data.common

import com.google.gson.annotations.SerializedName


data class BasePaginationResponse<T>(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val results: List<T>
)