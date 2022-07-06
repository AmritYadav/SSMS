package com.sts.ssms.ui.password.reset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.sts.ssms.R
import com.sts.ssms.ui.login.viewmodel.LoginViewModel
import com.sts.ssms.utils.afterTextChanged
import com.sts.ssms.utils.showToast
import kotlinx.android.synthetic.main.dialog_fragment_reset_password.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : DialogFragment() {

    private val resetPasswordViewModel by viewModel<ResetPasswordViewModel>()
    private val loginViewModel by sharedViewModel<LoginViewModel>()

    companion object {
        private const val KEY_USER_EMAIL = "USER_EMAIL"
        fun newInstance(email: String) = ResetPasswordFragment().apply {
            arguments = bundleOf(KEY_USER_EMAIL to email)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val email = it.getString(KEY_USER_EMAIL)
            et_reset_password_email.setText(email)
        }

        resetPasswordViewModel.resetFormState.observe(viewLifecycleOwner, Observer {
            val resetPasswordState = it ?: return@Observer

            // disable update button unless both new and confirm password is valid
            btn_reset_password_update.isEnabled = it.isDataValid

            if (resetPasswordState.newPasswordError != null) {
                et_reset_password_new.error = getString(resetPasswordState.newPasswordError)
            }
            if (resetPasswordState.confirmPasswordError != null) {
                et_reset_password_confirm.error = getString(resetPasswordState.confirmPasswordError)
            }
        })

        resetPasswordViewModel.resetPwdResult.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer
            if (result.success != null) {
                updateUserView(result.success)
                loginViewModel.passwordRequestStatus.value = true
                dialog?.dismiss()
            }
            if(result.error!=null) updateUserView(result.error)
        })

        et_reset_password_new.afterTextChanged {
            resetPasswordViewModel.resetPasswordDataChanged(
                et_reset_password_new.text.toString(),
                et_reset_password_confirm.text.toString()
            )
        }

        et_reset_password_confirm.afterTextChanged {
            resetPasswordViewModel.resetPasswordDataChanged(
                et_reset_password_new.text.toString(),
                et_reset_password_confirm.text.toString()
            )
        }

        btn_reset_password_update.setOnClickListener {
            resetPasswordViewModel.resetPassword(
                et_reset_password_email.text.toString(),
                et_reset_password_new.text.toString(),
                et_reset_password_confirm.text.toString()
            )
        }

        btn_reset_password_cancel.setOnClickListener {
            loginViewModel.passwordRequestStatus.value = false
            dialog?.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
    }

    private fun updateUserView(message: String) {
        activity?.showToast(message)
    }
}
