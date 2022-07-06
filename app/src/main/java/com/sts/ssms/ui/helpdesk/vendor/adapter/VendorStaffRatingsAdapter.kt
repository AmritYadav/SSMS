package com.sts.ssms.ui.helpdesk.vendor.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.data.helpdesk.vendor.api.RatingAndComment

class VendorStaffRatingsAdapter(private var ratingAndComments: List<RatingAndComment>) :
    RecyclerView.Adapter<VendorRatingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorRatingViewHolder {
        return VendorRatingViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return ratingAndComments.size
    }

    override fun onBindViewHolder(holder: VendorRatingViewHolder, position: Int) {
        holder.bind(ratingAndComments[position])
    }

    fun updateList(vendorRatingList: List<RatingAndComment>) {
        ratingAndComments = vendorRatingList
        notifyDataSetChanged()
    }

}