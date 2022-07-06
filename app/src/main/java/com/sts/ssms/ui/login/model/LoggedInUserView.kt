package com.sts.ssms.ui.login.model

/**
 * UserEntity details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val passwordChanged: Int
)
