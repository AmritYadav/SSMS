package com.sts.ssms.ui.helpdesk.vendor.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel
import com.sts.ssms.utils.formatString
import com.sts.ssms.utils.inflateView

class VendorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name = itemView.findViewById<TextView>(R.id.tv_vendor_staff_name)
    private val category = itemView.findViewById<TextView>(R.id.tv_vendor_staff_category)
    private val phone = itemView.findViewById<TextView>(R.id.tv_vendor_staff_phone)
    private val status = itemView.findViewById<TextView>(R.id.tv_vendor_staff_status)

    fun bind(
        vendorUiModel: VendorUiModel?,
        loadDetailsCallback: (vendorUiModel: VendorUiModel) -> Unit?
    ) {
        vendorUiModel?.let { vendor ->
            name.text = vendor.name
            category.formatString(R.string.vendor_staff_category, vendor.categoryName)
            phone.formatString(R.string.vendor_staff_phone, vendor.phoneNumber)
            status.formatString(R.string.vendor_staff_status, vendor.status)
            itemView.setOnClickListener { loadDetailsCallback.invoke(vendor) }
        }
    }

    fun bind(
        vendorUiModel: VendorUiModel?,
        searchItemClickCallback: SearchItemClickCallback<VendorUiModel>
    ) {
        vendorUiModel?.let { vendor ->
            name.text = vendor.name
            category.formatString(R.string.vendor_staff_category, vendor.categoryName)
            phone.formatString(R.string.vendor_staff_phone, vendor.phoneNumber)
            status.formatString(R.string.vendor_staff_status, vendor.status)
            itemView.setOnClickListener { searchItemClickCallback.onClick(vendor) }
        }
    }

    companion object {
        fun create(parent: ViewGroup): VendorViewHolder {
            return VendorViewHolder(parent.inflateView(R.layout.item_vendor_staff))
        }
    }
}