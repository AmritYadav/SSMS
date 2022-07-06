package com.sts.ssms.ui.password.change.model

data class ChangePwdFormResult(
    val oldPasswordError: Int? = null,
    val newPasswordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val oldNewPwdMatchError: Int? = null,
    val newConfirmPwdMatchError: Int? = null,
    val isDataValid: Boolean = false
)