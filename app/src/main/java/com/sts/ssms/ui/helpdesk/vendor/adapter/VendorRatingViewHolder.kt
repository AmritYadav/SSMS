package com.sts.ssms.ui.helpdesk.vendor.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.vendor.api.RatingAndComment
import com.sts.ssms.utils.inflateView

class VendorRatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val user = itemView.findViewById<TextView>(R.id.tv_vendor_details_user)
    private val userRating =
        itemView.findViewById<RatingBar>(R.id.rating_vendor_details_user_rating)
    private val comment = itemView.findViewById<TextView>(R.id.tv_vendor_details_user_comment)

    fun bind(ratingAndComment: RatingAndComment) {
        user.text = ratingAndComment.by
        userRating.rating = ratingAndComment.ratings.toFloat()
        comment.text = ratingAndComment.comments
    }

    companion object {
        fun create(parent: ViewGroup): VendorRatingViewHolder {
            return VendorRatingViewHolder(parent.inflateView(R.layout.item_vendor_details_rating_comment))
        }
    }
}