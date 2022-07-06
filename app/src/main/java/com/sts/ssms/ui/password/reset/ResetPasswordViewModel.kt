package com.sts.ssms.ui.password.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.R
import com.sts.ssms.data.password.reset.ResetPwdRepository
import com.sts.ssms.ui.password.reset.model.PasswordResult
import com.sts.ssms.ui.password.reset.model.ResetPasswordFormState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPasswordViewModel(private val resetPwdRepository: ResetPwdRepository) : ViewModel() {

    private val _resetForm = MutableLiveData<ResetPasswordFormState>()
    val resetFormState: LiveData<ResetPasswordFormState> = _resetForm

    private val _resetPasswordResult = MutableLiveData<PasswordResult>()
    val resetPwdResult: LiveData<PasswordResult> = _resetPasswordResult

    fun resetPasswordDataChanged(newPassword: String, confirmPassword: String) {
        if (!isPasswordValid(newPassword)) {
            _resetForm.value =
                ResetPasswordFormState(newPasswordError = R.string.invalid_password)
        } else if (!isPasswordValid(confirmPassword)) {
            _resetForm.value =
                ResetPasswordFormState(confirmPasswordError = R.string.invalid_password)
        } else if (!isNewConfirmPasswordSame(newPassword, confirmPassword)) {
            _resetForm.value =
                ResetPasswordFormState(isDataValid = false)
        } else {
            _resetForm.value =
                ResetPasswordFormState(isDataValid = true)
        }
    }

    fun resetPassword(email: String, newPwd: String, confirmPwd: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val resetResult = resetPwdRepository.resetPassword(email, newPwd, confirmPwd)
            withContext(Dispatchers.Main) {
                _resetPasswordResult.value = resetResult
            }
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    // A placeholder new and confirm password equality check
    private fun isNewConfirmPasswordSame(newPassword: String, confirmPassword: String): Boolean {
        return newPassword == confirmPassword
    }
}