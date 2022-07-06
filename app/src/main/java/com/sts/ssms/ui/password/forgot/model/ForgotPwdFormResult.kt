package com.sts.ssms.ui.password.forgot.model

data class ForgotPwdFormResult(
    val userEmailError: Int? = null,
    val isDataValid: Boolean = false
)