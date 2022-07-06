package com.sts.ssms.ui.helpdesk.amenity.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyAmenity
import com.sts.ssms.ui.helpdesk.amenity.viewholders.SocietyAmenityViewHolder
import com.sts.ssms.ui.helpdesk.paymentdetails.adapter.VIEW_TYPE_DATA
import com.sts.ssms.ui.helpdesk.paymentdetails.adapter.VIEW_TYPE_RETRY
import java.util.*

class SocietyAmenityAdapter(private val callback: () -> Unit?) :
    PagedListAdapter<SocietyAmenity, RecyclerView.ViewHolder>(SocietyAmenityDiffUtils) {
    private var networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> SocietyAmenityViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SocietyAmenityViewHolder -> {
                holder.binding.allAmenity = getItem(position)
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

object SocietyAmenityDiffUtils : DiffUtil.ItemCallback<SocietyAmenity>() {
    override fun areItemsTheSame(oldItem: SocietyAmenity, newItem: SocietyAmenity): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: SocietyAmenity, newItem: SocietyAmenity): Boolean {
        return oldItem.id == newItem.id
    }
}