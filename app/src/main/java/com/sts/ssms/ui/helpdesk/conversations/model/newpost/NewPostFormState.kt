package com.sts.ssms.ui.helpdesk.conversations.model.newpost

data class NewPostFormState(
    val titleError: Int? = null,
    val descriptionError: Int? = null,
    val isDataValid: Boolean = false
)