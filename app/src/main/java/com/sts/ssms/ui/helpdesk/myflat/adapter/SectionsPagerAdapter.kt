package com.sts.ssms.ui.helpdesk.myflat.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.myflat.owner.FlatOwnersFragment
import com.sts.ssms.ui.helpdesk.myflat.tenant.TenantFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_flat_owner,
    R.string.tab_flat_tenant
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) FlatOwnersFragment()
        else TenantFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}