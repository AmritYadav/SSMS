package com.sts.ssms.ui.helpdesk.documents.model

data class DocumentsResult (
    val documentList: List<Documents>? = null,
    val error: String? = null
)