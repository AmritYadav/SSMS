package com.sts.ssms.ui.search.photogallery

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.ui.helpdesk.photogallery.adapter.AlbumViewHolder
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

class SearchAlbumAdapter(
    private val searchItemClickCallback: SearchItemClickCallback<AlbumUiModel>,
    retryCallback: () -> Unit
) : SearchAdapter<AlbumUiModel>(retryCallback) {
    override val layoutID: Int = R.layout.item_album

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: AlbumUiModel?) {
        with((holder as AlbumViewHolder)) {
            bindSearchItem(item, searchItemClickCallback)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        AlbumViewHolder.create(parent)

}