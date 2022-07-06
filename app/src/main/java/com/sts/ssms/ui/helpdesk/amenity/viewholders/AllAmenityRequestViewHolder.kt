package com.sts.ssms.ui.helpdesk.amenity.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemAllAmenityRequestBinding
import com.sts.ssms.utils.inflateDataBindingView

class AllAmenityRequestViewHolder(val binding: ItemAllAmenityRequestBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) =
            AllAmenityRequestViewHolder(
                parent.inflateDataBindingView(R.layout.item_all_amenity_request)
            )
    }
}