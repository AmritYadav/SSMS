package com.sts.ssms.ui.login

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.google.android.material.button.MaterialButton
import com.sts.ssms.R
import com.sts.ssms.ui.login.model.LoggedInUserView
import com.sts.ssms.ui.login.viewmodel.LoginViewModel
import com.sts.ssms.ui.password.change.ChangePwdFragment
import com.sts.ssms.ui.password.forgot.ForgotPasswordDialogFragment
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModel<LoginViewModel>()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        findViewById<MaterialButton>(R.id.forgot_password).setOnClickListener {
            ForgotPasswordDialogFragment().apply {
                show(supportFragmentManager.beginTransaction(), "ForgotPasswordDialogFragment")
            }
        }

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            loading.gone(window) // set View visibility to GONE
            if (loginResult.error != null) {
                showToast(loginResult.error)
            }
            if (loginResult.success != null) {
                val passwordChanged = loginResult.success.passwordChanged
                if (passwordChanged == 1) {
                    updateUiWithUser(loginResult.success)
                    finish()
                    navigationTo(NAVIGATE_TO_HELP_DESK)
                } else {
                    showChangePasswordDialog()
                }
            }
        })

        loginViewModel.passwordRequestStatus.observe(this) { resetStatus ->
            // clear the input fields on successful password update
            if (resetStatus) resetLoginFields()
        }

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        if (login.isEnabled)
                            loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                            )
                }
                false
            }

            login.setOnClickListener {
                loading.visible(window)
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        showToast("$welcome $displayName", Toast.LENGTH_LONG)
    }

    /**
     * A password change dialog to change the system generated password
     * and set new password
     */
    private fun showChangePasswordDialog() {
        val user = loginViewModel.loggedInUser
        ChangePwdFragment.newInstance(fromProfile = false, email = user?.email).apply {
            show(supportFragmentManager.beginTransaction(), ChangePwdFragment::javaClass.name)
        }
    }

    /**
     * Clears the Login input fields to allow
     * Login with new/updated credentials
     */
    private fun resetLoginFields() {
        username.text.clear()
        password.text.clear()
    }
}
