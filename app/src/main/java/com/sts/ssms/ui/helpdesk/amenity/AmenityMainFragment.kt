package com.sts.ssms.ui.helpdesk.amenity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.amenity.adapters.SectionPagerAdapter
import kotlinx.android.synthetic.main.fragment_amenity_main.*

class AmenityMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_amenity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSectionsPagerView()
    }

    private fun setupSectionsPagerView() {
        view_pager_amenity.apply {
            adapter = SectionPagerAdapter(context, childFragmentManager)
        }
        tabs.setupWithViewPager(view_pager_amenity)
    }
}
