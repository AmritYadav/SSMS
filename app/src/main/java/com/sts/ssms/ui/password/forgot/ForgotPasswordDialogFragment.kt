package com.sts.ssms.ui.password.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.password.forgot.ForgotPwdRepository.Companion.KEY_USER_EMAIL
import com.sts.ssms.ui.verifyotp.VerifyOtpFragment
import com.sts.ssms.utils.afterTextChanged
import com.sts.ssms.utils.gone
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.visible
import kotlinx.android.synthetic.main.dialog_fragment_forgot_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordDialogFragment : DialogFragment() {

    private val viewModel by viewModel<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog.let { isCancelable = false }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.forgotPwdFormResult.observe(viewLifecycleOwner) { result ->

            btn_forgot_password_send_otp.isEnabled = result.isDataValid

            if (result.userEmailError != null)
                til_forgot_password_email.error = getString(result.userEmailError)
            else
                til_forgot_password_email.error = null
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> {
                    pb_forgot_pwd_loader.visible(activity?.window)
                }
                NetworkState.LOADED -> {
                    pb_forgot_pwd_loader.gone(activity?.window)
                    showVerifyOtpDialog()
                }
                else -> {
                    pb_forgot_pwd_loader.gone(activity?.window)
                    activity?.showToast(it.msg!!)
                }
            }
        }

        ti_et_forgot_password_email.afterTextChanged {
            viewModel.forgotPwdFormResult(it)
        }

        btn_forgot_password_send_otp.setOnClickListener {
            viewModel.forgotPwdRequestOtp(ti_et_forgot_password_email.text.toString())
        }
        btn_forgot_password_cancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun showVerifyOtpDialog() {
        activity?.let {
            val transaction = it.supportFragmentManager.beginTransaction()
            val dialogFragment = VerifyOtpFragment()
            val bundle = Bundle()
            bundle.putString(KEY_USER_EMAIL, ti_et_forgot_password_email.text.toString())
            dialogFragment.arguments = bundle
            dialogFragment.show(transaction, "VerifyOtpFragment")
            dialog?.dismiss()
        }
    }
}