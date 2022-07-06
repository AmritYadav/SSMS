package com.sts.ssms.base.ui.fragment

import android.os.Handler
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.ui.search.SearchActivity
import com.sts.ssms.ui.search.model.SearchItem
import com.sts.ssms.utils.NavType
import kotlinx.android.synthetic.main.fragment_base_main.*

abstract class BaseSearchFragment<T : SearchItem> : BaseFragment<T>() {

    override val navType: NavType? by lazy { (activity as SearchActivity).navType }

    override val filerId: Int by lazy { (activity as SearchActivity).filterId }

    internal fun search(query: String) {
        if (viewModel.showQuery(query)) {
            Handler().postDelayed({ rv_base.scrollToPosition(0) }, 300)
            @Suppress("UNCHECKED_CAST")
            (rv_base.adapter as SearchAdapter<T>).submitList(null)
        }
    }

    internal fun setFilterId(filterInt: Int) = viewModel.setSearchFilterId(filterInt)
}