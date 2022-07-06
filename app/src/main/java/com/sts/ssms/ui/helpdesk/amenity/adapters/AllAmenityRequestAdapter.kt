package com.sts.ssms.ui.helpdesk.amenity.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest
import com.sts.ssms.ui.helpdesk.amenity.viewholders.AllAmenityRequestViewHolder
import com.sts.ssms.ui.helpdesk.paymentdetails.adapter.VIEW_TYPE_DATA
import com.sts.ssms.ui.helpdesk.paymentdetails.adapter.VIEW_TYPE_RETRY
import java.util.*

class AllAmenityRequestAdapter(private val callback: () -> Unit?) :
    PagedListAdapter<AllAmenityRequest, RecyclerView.ViewHolder>(AllAmenityReqDiffUtils) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> AllAmenityRequestViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AllAmenityRequestViewHolder -> {
                holder.binding.allRequest = getItem(position)
            }
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

object AllAmenityReqDiffUtils : DiffUtil.ItemCallback<AllAmenityRequest>() {
    override fun areItemsTheSame(oldItem: AllAmenityRequest, newItem: AllAmenityRequest): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(
        oldItem: AllAmenityRequest,
        newItem: AllAmenityRequest
    ): Boolean {
        return oldItem.id == newItem.id
    }
}