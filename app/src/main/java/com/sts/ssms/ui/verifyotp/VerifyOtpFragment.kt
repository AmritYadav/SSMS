package com.sts.ssms.ui.verifyotp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.password.forgot.ForgotPwdRepository
import com.sts.ssms.ui.password.reset.ResetPasswordFragment
import com.sts.ssms.utils.gone
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.visible
import kotlinx.android.synthetic.main.dialog_fragment_send_otp.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyOtpFragment : DialogFragment() {

    private val viewModel by viewModel<VerifyOtpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog.let { isCancelable = false }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_send_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = arguments?.getString(ForgotPwdRepository.KEY_USER_EMAIL)
        var isResendClicked = false

        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> {
                    pb_verify_otp_loader.visible(activity?.window)
                }
                NetworkState.LOADED -> {
                    pb_verify_otp_loader.gone(activity?.window)
                    if (!isResendClicked)
                        showResetPasswordDialog(email)
                    else
                        activity?.showToast("OTP Sent. Please check your email")
                }
                else -> {
                    pb_verify_otp_loader.gone(activity?.window)
                    activity?.showToast(it.msg!!)
                }
            }
        }

        btn_verify_otp.setOnClickListener {
            isResendClicked = false
            viewModel.verifyOtp(email!!, ti_et_verify_otp_code.text.toString())
        }

        btn_resend_otp.setOnClickListener {
            isResendClicked = true
            viewModel.resendOtp(email!!)
            ti_et_verify_otp_code.text?.clear()
        }
    }

    private fun showResetPasswordDialog(email: String?) {
        activity?.let {
            val transaction = it.supportFragmentManager.beginTransaction()
            val dialogFragment = ResetPasswordFragment.newInstance(email!!)
            dialogFragment.show(transaction, "ResetPasswordFragment")
            dialog?.dismiss()
        }
    }
}