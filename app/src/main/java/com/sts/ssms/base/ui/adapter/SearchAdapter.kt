package com.sts.ssms.base.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.search.model.SearchItem
import java.util.*

abstract class SearchAdapter<T : SearchItem>(private val retryCallback: () -> Unit) :
    PagedListAdapter<T, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<T>() {
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            Objects.equals(oldItem, newItem)

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.id == newItem.id
    }) {

    private var networkState: NetworkState? = null

    protected abstract val layoutID: Int

    protected abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: T?)

    protected abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            layoutID -> onBindItemViewHolder(holder, getItem(position))
            R.layout.item_network_error -> (holder as NetworkErrorViewHolder).bind(
                networkState,
                retryCallback
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            layoutID -> onCreateViewHolder(parent)
            R.layout.item_network_error -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_error
        } else {
            layoutID
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

}