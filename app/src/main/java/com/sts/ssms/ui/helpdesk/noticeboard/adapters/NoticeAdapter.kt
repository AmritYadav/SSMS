package com.sts.ssms.ui.helpdesk.noticeboard.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import java.util.*

private const val VIEW_TYPE_DATA = 0x010
private const val VIEW_TYPE_RETRY = 0x011

class NoticeAdapter(
    private val callback: () -> Unit?,
    private val itemClickCallback: (notice: Notice) -> Unit
) :
    PagedListAdapter<Notice, RecyclerView.ViewHolder>(NoticeDiffUtils) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> NoticeViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoticeViewHolder -> holder.bind(getItem(position), itemClickCallback)
            is NetworkErrorViewHolder -> holder.bind(networkState, callback)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasNetworkErrorRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) VIEW_TYPE_DATA else VIEW_TYPE_RETRY
    }

    private fun hasNetworkErrorRow() =
        networkState != null && (networkState == NetworkState.LOADING || networkState == NetworkState.error(
            networkState?.msg
        ))

    fun updateNetworkState(newNetworkState: NetworkState?) {
        networkState = newNetworkState
        notifyItemChanged(super.getItemCount())
    }

}

object NoticeDiffUtils : DiffUtil.ItemCallback<Notice>() {
    override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
        return oldItem.id == newItem.id
    }
}