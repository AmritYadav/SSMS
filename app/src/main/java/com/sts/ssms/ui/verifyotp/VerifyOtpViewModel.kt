package com.sts.ssms.ui.verifyotp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.password.forgot.ForgotPwdRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VerifyOtpViewModel(private val forgotPwdRepository: ForgotPwdRepository) : ViewModel() {

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    fun verifyOtp(email: String, otp: String) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
             val result = forgotPwdRepository.verifyOtp(email, otp)
             if (result.success != null)
                 withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADED }
             else
                 withContext(Dispatchers.Main) {
                     _networkState.value = NetworkState.error(result.error)
                 }
        }
    }

    fun resendOtp(email: String) {
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
}