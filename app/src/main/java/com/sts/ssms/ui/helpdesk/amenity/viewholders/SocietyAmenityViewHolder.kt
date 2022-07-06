package com.sts.ssms.ui.helpdesk.amenity.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemAllAmenityBinding
import com.sts.ssms.utils.inflateDataBindingView

class SocietyAmenityViewHolder(val binding: ItemAllAmenityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) =
            SocietyAmenityViewHolder(
                parent.inflateDataBindingView(R.layout.item_all_amenity)
            )
    }
}