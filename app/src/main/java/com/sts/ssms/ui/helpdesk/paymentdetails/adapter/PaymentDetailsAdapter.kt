package com.sts.ssms.ui.helpdesk.paymentdetails.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.paymentdetails.model.PaymentUiModel
import java.util.*

const val VIEW_TYPE_DATA = 0x010
const val VIEW_TYPE_RETRY = 0x011

class PaymentDetailsAdapter(
    private val callback: () -> Unit?,
    private val downloadCallback: (paymentId: Int) -> Unit
) : PagedListAdapter<PaymentUiModel, RecyclerView.ViewHolder>(PaymentDiffUtils) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> PaymentViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PaymentViewHolder -> {
                val paymentUiModel = getItem(position)
                holder.bind(paymentUiModel, downloadCallback) {
                    paymentUiModel?.let {
                        val isExpanded = it.isExpanded
                        it.isExpanded = !isExpanded
                        notifyItemChanged(position)
                    }
                }
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

object PaymentDiffUtils : DiffUtil.ItemCallback<PaymentUiModel>() {
    override fun areItemsTheSame(oldItem: PaymentUiModel, newItem: PaymentUiModel): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: PaymentUiModel, newItem: PaymentUiModel): Boolean {
        return oldItem.id == newItem.id
    }
}