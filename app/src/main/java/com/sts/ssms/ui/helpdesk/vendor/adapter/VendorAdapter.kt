package com.sts.ssms.ui.helpdesk.vendor.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel
import java.util.*

private const val VIEW_TYPE_DATA = 0x010
private const val VIEW_TYPE_RETRY = 0x011

class VendorAdapter(
    private val callback: () -> Unit?,
    private val loadDetailsCallback: (vendor: VendorUiModel) -> Unit?
) :
    PagedListAdapter<VendorUiModel, RecyclerView.ViewHolder>(VendorDiffUtils) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> VendorViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VendorViewHolder -> holder.bind(getItem(position), loadDetailsCallback)
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

object VendorDiffUtils : DiffUtil.ItemCallback<VendorUiModel>() {
    override fun areItemsTheSame(oldItem: VendorUiModel, newItem: VendorUiModel): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: VendorUiModel, newItem: VendorUiModel): Boolean {
        return oldItem.id == newItem.id
    }
}