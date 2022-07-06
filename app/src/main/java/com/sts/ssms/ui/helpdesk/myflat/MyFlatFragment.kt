package com.sts.ssms.ui.helpdesk.myflat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.databinding.FragmentMyFlatBinding
import com.sts.ssms.ui.helpdesk.myflat.adapter.SectionsPagerAdapter
import com.sts.ssms.utils.gone
import com.sts.ssms.utils.showListPopupWindow
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFlatFragment : Fragment() {

    private lateinit var flatList: List<Flat>

    private val viewModel by viewModel<MyFlatViewModel>()

    private lateinit var myFlatBinding: FragmentMyFlatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFlatBinding = FragmentMyFlatBinding.inflate(inflater, container, false)
        myFlatBinding.lifecycleOwner = viewLifecycleOwner
        return myFlatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSectionViewPager()
        setObservers()
        setupSwipeToRefresh()

        viewModel.getUserFlats()

        myFlatBinding.btnFlatDropdown.setOnClickListener {
            it.showListPopupWindow(flatList) { itemId -> viewModel.myFlatMembers(itemId) }
        }
    }

    private fun setupSectionViewPager() {
        myFlatBinding.viewPagerMyFlat.apply {
            adapter = SectionsPagerAdapter(context, childFragmentManager)
        }
        myFlatBinding.tabs.setupWithViewPager(myFlatBinding.viewPagerMyFlat)
    }

    private fun setObservers() {
        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> myFlatBinding.pbFlatMemberLoader.visible(activity?.window)
                NetworkState.LOADED -> myFlatBinding.pbFlatMemberLoader.gone(activity?.window)
                else -> {
                    val selectedFlatId = viewModel.getSelectedFlatId()
                    flatList.find { flat -> flat.flatId == selectedFlatId }?.let { selectedFlat ->
                        myFlatBinding.btnFlatDropdown.text = selectedFlat.display
                    }
                    myFlatBinding.pbFlatMemberLoader.gone(activity?.window)
                    it.msg?.let { it1 -> updateUserView(it1) }
                }
            }
        }

        viewModel.flatResult.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                flatList = it
                myFlatBinding.btnFlatDropdown.text = it[0].display
                viewModel.myFlatMembers(it[0].flatId)
            } else {
                flatList = emptyList()
            }
        }
    }

    private fun setupSwipeToRefresh() {
        myFlatBinding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.myFlatMembers(viewModel.getSelectedFlatId())
                isRefreshing = false
            }
        }
    }

    private fun updateUserView(errorMsg: String) {
        activity?.showToast(errorMsg)
    }

}
