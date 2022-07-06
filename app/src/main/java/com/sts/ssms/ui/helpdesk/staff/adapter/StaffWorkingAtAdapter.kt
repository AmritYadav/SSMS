package com.sts.ssms.ui.helpdesk.staff.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.data.helpdesk.staff.api.WorkingAt

class StaffWorkingAtAdapter(private var list: List<WorkingAt>) :
    RecyclerView.Adapter<StaffWorkingAtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffWorkingAtViewHolder {
        return StaffWorkingAtViewHolder.create(parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: StaffWorkingAtViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(workingAtList: List<WorkingAt>) {
        list = workingAtList
        notifyDataSetChanged()
    }
}