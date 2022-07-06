package com.sts.ssms.ui.helpdesk.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.sts.ssms.data.common.LoadingState
import com.sts.ssms.databinding.FragmentProfileBinding
import com.sts.ssms.ui.helpdesk.profile.model.Profile
import com.sts.ssms.ui.helpdesk.profile.viewmodel.ProfileViewModel
import com.sts.ssms.ui.password.change.ChangePwdFragment
import com.sts.ssms.utils.NAVIGATE_TO_LOGIN
import com.sts.ssms.utils.navigationTo
import com.sts.ssms.utils.showToast
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val viewModel by viewModel<ProfileViewModel>()

    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var fm: FragmentManager
    private lateinit var profile: Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Start fetching profile details from the Network,
        // In case of network error, loads data from the cache
        viewModel.loadProfile()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        profileBinding.lifecycleOwner = viewLifecycleOwner
        profileBinding.profileViewModel = viewModel
        fm = childFragmentManager
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupSwipeToRefresh()

        profileBinding.btnProfileChangePassword.setOnClickListener {
            ChangePwdFragment.newInstance(fromProfile = true, email = profile.email).apply {
                show(fm.beginTransaction(), ChangePwdFragment::javaClass.name)
            }
        }

        profileBinding.btnProfileSwitchSociety.setOnClickListener {
            SocietyFragment().show(
                childFragmentManager.beginTransaction(),
                SocietyFragment::javaClass.name
            )
        }
    }

    private fun setupObservers() {
        // Observes profile request status and notifies user in case of error
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == LoadingState.ERROR) {
                    activity?.showToast("Error fetching Profile from the network")
                }
            }
        })
        viewModel.profile.observe(viewLifecycleOwner) {
            profile = it
        }
        viewModel.passwordStatus.observe(viewLifecycleOwner) {
            if (it) logoutUserNavigateToLogin()
        }
    }

    private fun setupSwipeToRefresh() {
        swipe_refresh.apply {
            setOnRefreshListener {
                viewModel.loadProfile()
                isRefreshing = false
            }
        }
    }

    private fun logoutUserNavigateToLogin() {
        viewModel.logout()
        activity?.showToast(
            "Password updated successfully. Please login again",
            Toast.LENGTH_LONG
        )
        activity?.finish()
        activity?.navigationTo(NAVIGATE_TO_LOGIN)
    }
}
