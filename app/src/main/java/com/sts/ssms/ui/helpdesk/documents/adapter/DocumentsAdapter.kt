package com.sts.ssms.ui.helpdesk.documents.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import java.util.Objects


private const val VIEW_TYPE_DATA = 0x010
private const val VIEW_TYPE_RETRY = 0x011

class DocumentsAdapter(
    private val callback: () -> Unit?,
    private val onDownloadClick: (docUrl: String) -> Unit
) : PagedListAdapter<Documents, RecyclerView.ViewHolder>(DocumentsDiffUtils) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> DocumentsViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DocumentsViewHolder -> holder.bind(getItem(position), onDownloadClick)
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

object DocumentsDiffUtils : DiffUtil.ItemCallback<Documents>() {
    override fun areItemsTheSame(oldItem: Documents, newItem: Documents): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Documents, newItem: Documents): Boolean {
        return oldItem.id == newItem.id
    }
}