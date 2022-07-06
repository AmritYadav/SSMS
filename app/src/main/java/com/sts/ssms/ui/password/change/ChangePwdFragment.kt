package com.sts.ssms.ui.password.change

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.profile.viewmodel.ProfileViewModel
import com.sts.ssms.ui.login.viewmodel.LoginViewModel
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.dialog_fragment_change_password.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePwdFragment : DialogFragment() {

    private val viewModel by viewModel<ChangePwdViewModel>()
    private val loginViewModel by sharedViewModel<LoginViewModel>()
    private val profileViewModel by sharedViewModel<ProfileViewModel>()

    companion object {
        private const val KEY_FROM_PROFILE = "FROM_PROFILE"
        private const val KEY_USER_EMAIL = "USER_EMAIL"
        fun newInstance(fromProfile: Boolean, email: String?): ChangePwdFragment {
            return ChangePwdFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(KEY_FROM_PROFILE, fromProfile)
                    putString(KEY_USER_EMAIL, email)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromProfile = arguments?.getBoolean(KEY_FROM_PROFILE)
        val email = arguments?.getString(KEY_USER_EMAIL)

        setupObserver(fromProfile)

        tv_change_password_email.text = email

        ti_et_change_password_current.afterTextChanged {
            viewModel.changePwdDataChange(
                ti_et_change_password_current.text.toString(),
                ti_et_change_password_new.text.toString(),
                ti_et_change_password_confirm.text.toString()
            )
        }

        ti_et_change_password_new.afterTextChanged {
            viewModel.changePwdDataChange(
                ti_et_change_password_current.text.toString(),
                ti_et_change_password_new.text.toString(),
                ti_et_change_password_confirm.text.toString()
            )
        }

        ti_et_change_password_confirm.afterTextChanged {
            viewModel.changePwdDataChange(
                ti_et_change_password_current.text.toString(),
                ti_et_change_password_new.text.toString(),
                ti_et_change_password_confirm.text.toString()
            )
        }

        btn_change_password_update.setOnClickListener {
            viewModel.changePassword(
                ti_et_change_password_current.text.toString(),
                ti_et_change_password_new.text.toString(),
                ti_et_change_password_confirm.text.toString()
            )
        }

        btn_change_password_cancel.setOnClickListener {
            fromProfile?.let {
                if (!fromProfile)
                    loginViewModel.passwordRequestStatus.value = false
                else
                    profileViewModel.passwordStatus.value = false
            }
            dialog?.dismiss()
        }
    }

    private fun setupObserver(fromProfile: Boolean?) {
        viewModel.changePasswordFormState.observe(viewLifecycleOwner) { result ->

            btn_change_password_update.isEnabled = result.isDataValid

            til_change_password_current.error =
                if (result.oldPasswordError != null) getString(result.oldPasswordError) else null
            til_change_password_new.error =
                if (result.newPasswordError != null) getString(result.newPasswordError) else null
            til_change_password_confirm.error =
                if (result.confirmPasswordError != null) getString(result.confirmPasswordError) else null
            if (result.oldNewPwdMatchError != null)
                til_change_password_new.error = getString(result.oldNewPwdMatchError)
            if (result.newConfirmPwdMatchError != null)
                til_change_password_confirm.error = getString(result.newConfirmPwdMatchError)
        }

        viewModel.changePwdResult.observe(viewLifecycleOwner) { result ->
            if (result.success != null) {
                fromProfile?.let {
                    if (!fromProfile)
                        loginViewModel.passwordRequestStatus.value = true
                    else
                        profileViewModel.passwordStatus.value = true
                }
                dialog?.dismiss()
            } else result.error?.let { changePasswordError(it) }
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> pb_change_pwd_loader.visible(activity?.window)
                NetworkState.LOADED -> pb_change_pwd_loader.gone(activity?.window)
            }
        }
    }

    private fun changePasswordError(errorMsg: String) {
        activity?.showToast(errorMsg)
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
    }
}
