package com.sts.ssms.ui.password.forgot

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.password.forgot.ForgotPwdRepository
import com.sts.ssms.ui.password.forgot.model.ForgotPwdFormResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordViewModel(private val forgotPwdRepository: ForgotPwdRepository) : ViewModel() {

    private var _forgotPwdFormResult = MutableLiveData<ForgotPwdFormResult>()
    val forgotPwdFormResult: LiveData<ForgotPwdFormResult> = _forgotPwdFormResult

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    fun forgotPwdFormResult(email: String) {
        if (!isUserEmailValid(email)) {
            _forgotPwdFormResult.value =
                ForgotPwdFormResult(userEmailError = R.string.invalid_user_id)
        } else {
            _forgotPwdFormResult.value = ForgotPwdFormResult(isDataValid = true)
        }
    }

    fun forgotPwdRequestOtp(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = forgotPwdRepository.requestOtp(email)
            if (result.success != null)
                withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADED }
            else
                withContext(Dispatchers.Main) {
                    _networkState.value = NetworkState.error(result.error)
                }
        }
    }

    // A placeholder user email validation check
    private fun isUserEmailValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }
}