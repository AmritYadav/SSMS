package com.sts.ssms.paging.payments.model

import java.io.InputStream

data class BillDownloadResult(
    val success: InputStream? = null,
    val error: String? = null
)