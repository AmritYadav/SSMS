package com.sts.ssms.ui.password.reset.model

data class ResetPasswordFormState(
    val newPasswordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val isDataValid: Boolean = false
)