package com.sts.ssms.ui.helpdesk.staff.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import java.util.*

private const val VIEW_TYPE_DATA = 0x010
private const val VIEW_TYPE_RETRY = 0x011

class StaffAdapter(
    private val callback: () -> Unit?,
    private val loadDetailsCallback: (staff: StaffUiModel) -> Unit?
) : PagedListAdapter<StaffUiModel, RecyclerView.ViewHolder>(StaffDiffUtils) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> StaffViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StaffViewHolder -> holder.bind(getItem(position), loadDetailsCallback)
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

object StaffDiffUtils : DiffUtil.ItemCallback<StaffUiModel>() {
    override fun areItemsTheSame(oldItem: StaffUiModel, newItem: StaffUiModel): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: StaffUiModel, newItem: StaffUiModel): Boolean {
        return oldItem.id == newItem.id
    }
}