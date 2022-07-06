package com.sts.ssms.ui.search.amenity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest
import com.sts.ssms.ui.helpdesk.amenity.viewholders.AllAmenityRequestViewHolder

class SearchAllAmenityReqAdapter(
    retryCallback: () -> Unit
) : SearchAdapter<AllAmenityRequest>(retryCallback) {

    override val layoutID: Int = R.layout.item_all_amenity_request

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: AllAmenityRequest?) {
        (holder as AllAmenityRequestViewHolder).binding.allRequest = item
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        AllAmenityRequestViewHolder.create(parent)
}
