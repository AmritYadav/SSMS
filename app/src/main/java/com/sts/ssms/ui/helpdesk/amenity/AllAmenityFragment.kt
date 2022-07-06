package com.sts.ssms.ui.helpdesk.amenity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.amenity.adapters.SocietyAmenityAdapter
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyBuilding
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.SocietyAmenityViewModel
import com.sts.ssms.utils.showSocietyBuildingPopupWindow
import com.sts.ssms.utils.updateEmptyState
import kotlinx.android.synthetic.main.fragment_all_amenity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllAmenityFragment : Fragment() {

    private val amenityViewModel by viewModel<SocietyAmenityViewModel>()

    private lateinit var societyAmenityAdapter: SocietyAmenityAdapter
    private lateinit var societyBuildings: List<SocietyBuilding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amenityViewModel.loadSocietyBuildings()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_amenity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        configureRecycler()
        configureSwipeRefresh()

        society_building_filter.setOnClickListener {
            it.showSocietyBuildingPopupWindow(societyBuildings) { id ->
                amenityViewModel.filterBySocietyBuildingId(id)
            }
        }
    }

    private fun configureRecycler() {
        societyAmenityAdapter = SocietyAmenityAdapter { amenityViewModel.retry() }
        rv_all_amenity.apply {
            adapter = societyAmenityAdapter
        }
    }

    private fun setupObservables() {
        amenityViewModel.networkState?.observe(viewLifecycleOwner) { it ->
            societyAmenityAdapter.updateNetworkState(it)
            amenityViewModel.allAmenities.value?.let {
                view_empty_state.updateEmptyState(
                    it.isEmpty(),
                    getString(R.string.prompt_empty_state_amenity)
                )
            }
        }
        amenityViewModel.allAmenities.observe(viewLifecycleOwner) {
            societyAmenityAdapter.submitList(it)
        }
        amenityViewModel.societyBuildingResult.observe(viewLifecycleOwner) {
            societyBuildings = it
            society_building_filter.text =
                it.filter { societyBuilding -> societyBuilding.id == amenityViewModel.getSocietyBuildingId() }[0].name
        }
    }

    private fun configureSwipeRefresh() {
        swipe_refresh.apply {
            amenityViewModel.refreshState?.observe(viewLifecycleOwner) {
                isRefreshing = it == NetworkState.LOADING
            }
            setOnRefreshListener {
                amenityViewModel.refreshAllData()
            }
        }
    }
}
