package com.sts.ssms.ui.helpdesk.photogallery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.databinding.ItemAlbumBinding
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import com.sts.ssms.utils.inflateDataBindingView

class AlbumViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

//    ye view holder use kar sakte hai photoadapter mein
    fun bindSearchItem(
        albumUiModel: AlbumUiModel?,
        searchItemClickCallback: SearchItemClickCallback<AlbumUiModel>
    ) {
        albumUiModel?.let { binding.albumUiModel = it }
        binding.itemClickListener = searchItemClickCallback
    }

    companion object {
        fun create(parent: ViewGroup): AlbumViewHolder =
            AlbumViewHolder(parent.inflateDataBindingView(R.layout.item_album))
    }
}