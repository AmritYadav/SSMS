package com.sts.ssms.ui.helpdesk.staff.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.data.helpdesk.staff.api.Document

class StaffDocumentAdapter(
    private var staffDocumentList: List<Document>,
    private val downloadCallback: (url: String?) -> Unit
) : RecyclerView.Adapter<StaffDocumentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffDocumentViewHolder {
        return StaffDocumentViewHolder.create(parent)
    }

    override fun getItemCount(): Int = staffDocumentList.size

    override fun onBindViewHolder(holder: StaffDocumentViewHolder, position: Int) {
        holder.bind(staffDocumentList[position], downloadCallback)
    }

    fun updateList(staffDocumentList: List<Document>) {
        this.staffDocumentList = staffDocumentList
        notifyDataSetChanged()
    }
}