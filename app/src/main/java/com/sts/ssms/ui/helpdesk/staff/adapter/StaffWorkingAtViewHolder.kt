package com.sts.ssms.ui.helpdesk.staff.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.staff.api.WorkingAt
import com.sts.ssms.utils.inflateView

class StaffWorkingAtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val memberName = itemView.findViewById<TextView>(R.id.tv_staff_working_at_member_name)
    private val memberContact = itemView.findViewById<TextView>(R.id.tv_staff_working_at_contact)

    fun bind(workingAt: WorkingAt) {
        memberName.text = workingAt.name
        memberContact.text = workingAt.phoneNumber
    }

    companion object {
        fun create(parent: ViewGroup): StaffWorkingAtViewHolder = StaffWorkingAtViewHolder(
            parent.inflateView(R.layout.item_staff_working_at)
        )
    }
}