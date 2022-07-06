package com.sts.ssms.ui.helpdesk.bylaws.model

import java.io.InputStream

data class ByLawFormDownloadResult(
    val success: InputStream? = null,
    val error: String? = null
)