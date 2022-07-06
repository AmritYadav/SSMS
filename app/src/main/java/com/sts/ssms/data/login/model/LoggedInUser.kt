package com.sts.ssms.data.login.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val email: String,
    val displayName: String,
    val avatar: String?,
    val passwordChanged: Int
)
