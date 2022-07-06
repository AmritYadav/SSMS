package com.sts.ssms.ui.helpdesk.staff.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import com.sts.ssms.utils.formatString
import com.sts.ssms.utils.inflateView

class StaffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name = itemView.findViewById<TextView>(R.id.tv_vendor_staff_name)
    private val category = itemView.findViewById<TextView>(R.id.tv_vendor_staff_category)
    private val phone = itemView.findViewById<TextView>(R.id.tv_vendor_staff_phone)
    private val status = itemView.findViewById<TextView>(R.id.tv_vendor_staff_status)

    fun bind(
        staffUiModel: StaffUiModel?, loadDetailsCallback: (staffUiModel: StaffUiModel) -> Unit?
    ) {
        staffUiModel?.let { staff ->
            name.text = staff.name
            category.formatString(R.string.vendor_staff_category, staff.categoryName)
            phone.formatString(R.string.vendor_staff_phone, staff.phoneNumber)
            status.formatString(R.string.vendor_staff_status, staff.status)
            itemView.setOnClickListener { loadDetailsCallback.invoke(staff) }
        }
    }

    fun bind(
        staffUiModel: StaffUiModel?,
        searchItemClickCallback: SearchItemClickCallback<StaffUiModel>
    ) {
        staffUiModel?.let { staff ->
            name.text = staff.name
            category.formatString(R.string.vendor_staff_category, staff.categoryName)
            phone.formatString(R.string.vendor_staff_phone, staff.phoneNumber)
            status.formatString(R.string.vendor_staff_status, staff.status)
            itemView.setOnClickListener { searchItemClickCallback.onClick(staff) }
        }
    }

    companion object {
        fun create(parent: ViewGroup): StaffViewHolder {
            return StaffViewHolder(parent.inflateView(R.layout.item_vendor_staff))
        }
    }
}