package com.sts.ssms.ui.helpdesk.amenity.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sts.ssms.ui.helpdesk.amenity.model.MyAmenity
import com.sts.ssms.ui.helpdesk.amenity.viewholders.MyAmenityViewHolder
import java.util.*

class MyRequestedAmenityAdapter :
    ListAdapter<MyAmenity, MyAmenityViewHolder>(MyAmenityReqDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAmenityViewHolder =
        MyAmenityViewHolder.create(parent)

    override fun onBindViewHolder(holder: MyAmenityViewHolder, position: Int) {
        val amenity = getItem(position)
        holder.bind(amenity) {
            val isExpanded = amenity.isExpanded
            amenity.isExpanded = !isExpanded
            notifyItemChanged(position)
        }
    }

    object MyAmenityReqDiffUtils : DiffUtil.ItemCallback<MyAmenity>() {
        override fun areItemsTheSame(oldItem: MyAmenity, newItem: MyAmenity): Boolean {
            return Objects.equals(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: MyAmenity, newItem: MyAmenity): Boolean {
            return oldItem.amenityId == newItem.amenityId
        }
    }
}
