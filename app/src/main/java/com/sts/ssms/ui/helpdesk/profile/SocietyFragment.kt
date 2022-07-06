package com.sts.ssms.ui.helpdesk.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.profile.adapter.SocietyAdapter
import com.sts.ssms.ui.helpdesk.profile.viewmodel.SocietyViewModel
import com.sts.ssms.utils.gone
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.visible
import kotlinx.android.synthetic.main.fragment_society.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SocietyFragment : BottomSheetDialogFragment() {

    private val societyViewModel by viewModel<SocietyViewModel>()

    private lateinit var societyAdapter: SocietyAdapter
    private lateinit var societyName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_society, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        societyViewModel.userSocieties(onSocietySwitch = false)

        societyViewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> loading.visible(activity?.window)
                NetworkState.LOADED -> loading.gone(activity?.window)
            }
        }

        societyViewModel.switchSocietyResult.observe(viewLifecycleOwner) {
            if (it.success != null) {
                societyAdapter.setCurrentSocietyId(it.success)
                activity?.showToast("Welcome to $societyName")
            }
            if (it.error != null) {
                activity?.showToast("Error switching society")
            }
            dismiss()
        }

        societyViewModel.societyList.observe(viewLifecycleOwner) {
            societyAdapter.updateList(it)
        }
    }

    private fun setupRecycler() {
        societyAdapter =
            SocietyAdapter(emptyList()) { society ->
                societyName = society.name
                societyViewModel.userSocieties(
                    society.societyId,
                    true
                )
            }
        societyViewModel.societyId?.let {
            societyAdapter.setCurrentSocietyId(it.toInt())
        }
        rv_society.apply {
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            adapter = societyAdapter
        }
    }
}
