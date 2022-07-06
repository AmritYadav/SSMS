package com.sts.ssms.data.login.model

data class LoginApiResult(
    val loggedInUser: LoggedInUser? = null,
    val error: String? = null
)