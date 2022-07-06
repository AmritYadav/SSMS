package com.sts.ssms.ui.password.change

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.password.change.ChangePwdRepository
import com.sts.ssms.ui.password.change.model.ChangePwdFormResult
import com.sts.ssms.ui.password.reset.model.PasswordResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePwdViewModel(private val changePwdRepository: ChangePwdRepository) : ViewModel() {

    private val _changePwdForm = MutableLiveData<ChangePwdFormResult>()
    val changePasswordFormState: LiveData<ChangePwdFormResult> = _changePwdForm

    private val _changePwdResult = MutableLiveData<PasswordResult>()
    val changePwdResult: LiveData<PasswordResult> = _changePwdResult

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    fun changePwdDataChange(oldPassword: String, newPassword: String, confirmPassword: String) {
        if (!isPasswordValid(oldPassword))
            _changePwdForm.value =
                ChangePwdFormResult(oldPasswordError = R.string.invalid_password)
        else if (!isPasswordValid(newPassword))
            _changePwdForm.value =
                ChangePwdFormResult(newPasswordError = R.string.invalid_password)
        else if (!isPasswordValid(confirmPassword))
            _changePwdForm.value =
                ChangePwdFormResult(confirmPasswordError = R.string.invalid_password)
        else if (isOldNewPasswordSame(oldPassword, newPassword))
            _changePwdForm.value =
                ChangePwdFormResult(oldNewPwdMatchError = R.string.old_new_pwd_match_error)
        else if (!isNewConfirmPasswordSame(newPassword, confirmPassword))
            _changePwdForm.value =
                ChangePwdFormResult(newConfirmPwdMatchError = R.string.unmatched_new_confirm_password)
        else
            _changePwdForm.value = ChangePwdFormResult(isDataValid = true)
    }

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        _networkState.value = NetworkState.LOADING
        CoroutineScope(Dispatchers.IO).launch {
            val resetResult =
                changePwdRepository.changePassword(oldPassword, newPassword, confirmPassword)
            withContext(Dispatchers.Main) {
                _changePwdResult.value = resetResult
                _networkState.value = NetworkState.LOADED
            }
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isOldNewPasswordSame(oldPassword: String, newPassword: String): Boolean {
        if (isPasswordValid(oldPassword) && isPasswordValid(newPassword))
            return oldPassword == newPassword
        return false
    }

    private fun isNewConfirmPasswordSame(newPassword: String, confirmPassword: String): Boolean {
        if (isPasswordValid(newPassword) && isPasswordValid(confirmPassword))
            return newPassword == confirmPassword
        return false
    }
}