package com.sts.ssms.ui.search.noticeboard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.ui.helpdesk.noticeboard.adapters.NoticeViewHolder
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice

class SearchNoticeAdapter(
    private val searchItemClickCallback: SearchItemClickCallback<Notice>,
    retryCallback: () -> Unit
) : SearchAdapter<Notice>(retryCallback) {

    override val layoutID = R.layout.item_notice

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Notice?) {
        with(holder as NoticeViewHolder) {
            bind(item, searchItemClickCallback)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        NoticeViewHolder.create(parent)
}