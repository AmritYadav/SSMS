package com.sts.ssms.base.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.KEY_SEARCH_ITEM
import com.sts.ssms.ui.search.SearchDetailsActivity
import com.sts.ssms.ui.search.model.SearchItem
import com.sts.ssms.utils.NavType
import com.sts.ssms.utils.visibleGone
import kotlinx.android.synthetic.main.fragment_base_main.*

private const val REQUEST_SEARCH_DETAILS = 0x01001

abstract class BaseFragment<T : SearchItem> : Fragment(), SearchItemClickCallback<T> {

    protected abstract val viewModel: SearchViewModel<T>

    protected abstract fun getAdapter(retryCallback: () -> Unit): SearchAdapter<T>

    protected abstract val navType: NavType?

    protected abstract val filerId: Int

    protected abstract val layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_base_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemAdapter = getAdapter {
            viewModel.retry()
        }

        viewModel.items.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            itemAdapter.setNetworkState(it)
            when (it) {
                NetworkState.LOADED -> {
                    rv_base.scrollToPosition(0)
                    empty_state_msg.isVisible = itemAdapter.itemCount == 0
                }
                else -> it.msg?.let { empty_state_msg.visibleGone(false) }
            }
        }

        swipe_refresh_main.apply {
            viewModel.refreshState.observe(viewLifecycleOwner) {
                isRefreshing = it == NetworkState.LOADING
            }

            setOnRefreshListener { viewModel.refresh() }
        }

        rv_base.apply {
            layoutManager = this@BaseFragment.layoutManager
            adapter = itemAdapter
        }
    }

    override fun onClick(item: T) {
        val intent = Intent(activity, SearchDetailsActivity::class.java).apply {
            putExtras(Bundle().apply {
                putParcelable(KEY_SEARCH_ITEM, item)
                putParcelable(KEY_NAV_TYPE, navType)
            })
        }
        startActivityForResult(intent, REQUEST_SEARCH_DETAILS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SEARCH_DETAILS && resultCode == RESULT_OK) {
            viewModel.refresh()
        }
    }
}