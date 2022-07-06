package com.sts.ssms.ui.helpdesk.amenity.viewholders

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemMyRequestedAmenityBinding
import com.sts.ssms.ui.helpdesk.amenity.model.MyAmenity
import com.sts.ssms.utils.inflateDataBindingView
import com.sts.ssms.utils.visibleGone

class MyAmenityViewHolder(val binding: ItemMyRequestedAmenityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(myAmenity: MyAmenity, callback: () -> Unit) {
        binding.myAmenity = myAmenity
        itemView.findViewById<LinearLayout>(R.id.booking_details_content)
            .visibleGone(myAmenity.isExpanded)
        binding.bookingDetailsArrow.setOnClickListener {
            callback.invoke()
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            MyAmenityViewHolder(
                parent.inflateDataBindingView(R.layout.item_my_requested_amenity)
            )
    }
}