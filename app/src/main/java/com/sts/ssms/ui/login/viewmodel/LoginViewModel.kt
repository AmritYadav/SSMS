package com.sts.ssms.ui.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.R
import com.sts.ssms.data.login.model.LoggedInUser
import com.sts.ssms.data.login.repository.LoginRepository
import com.sts.ssms.ui.login.model.LoggedInUserView
import com.sts.ssms.ui.login.model.LoginFormState
import com.sts.ssms.ui.login.model.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    // shared between Login and Reset Password fragment
    val passwordRequestStatus = MutableLiveData<Boolean>()

    val loggedInUser: LoggedInUser?
        get() = loginRepository.user

    fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // can be launched in a separate asynchronous job
            val result = loginRepository.login(username, password)
            withContext(Dispatchers.Main) {
                if (result.loggedInUser != null) {
                    _loginResult.value =
                        LoginResult(
                            success = LoggedInUserView(
                                displayName = result.loggedInUser.displayName,
                                passwordChanged = result.loggedInUser.passwordChanged
                            )
                        )
                }
                if (result.error != null) {
                    _loginResult.value =
                        LoginResult(error = result.error)
                }
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                LoginFormState(usernameError = R.string.invalid_user_id)
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value =
                LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
