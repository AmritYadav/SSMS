package com.sts.ssms.ui.helpdesk.photogallery.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import java.util.*

private const val VIEW_TYPE_DATA = 0x020
private const val VIEW_TYPE_RETRY = 0x021

class PhotoGalleryAdapter(
    private val itemClick: SearchItemClickCallback<AlbumUiModel>,
    private val callback: () -> Unit
) : PagedListAdapter<AlbumUiModel, RecyclerView.ViewHolder>(PhotoGalleryDiffUtil) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> AlbumViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> holder.bindSearchItem(getItem(position), itemClick)
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

object PhotoGalleryDiffUtil : DiffUtil.ItemCallback<AlbumUiModel>() {
    override fun areItemsTheSame(oldItem: AlbumUiModel, newItem: AlbumUiModel): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: AlbumUiModel, newItem: AlbumUiModel): Boolean {
        return oldItem.id == newItem.id
    }
}
