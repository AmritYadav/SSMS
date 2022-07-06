package com.sts.ssms.ui.helpdesk.profile.adapter

import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.data.society.model.Society

class SocietyAdapter(
    private var societyList: List<Society>,
    private val callback: (society: Society) -> Unit
) :
    RecyclerView.Adapter<SocietyViewHolder>() {

    private var selectedSocietyId: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocietyViewHolder {
        return SocietyViewHolder.create(parent)
    }

    override fun getItemCount(): Int = societyList.size

    override fun onBindViewHolder(holder: SocietyViewHolder, position: Int) {
        val society = societyList[position]
        holder.bind(society, selectedSocietyId == society.societyId.toInt())
        holder.itemView.setOnClickListener {
            val checkedTextView = it as CheckedTextView
            if (!checkedTextView.isChecked) {
                checkedTextView.isChecked
                selectedSocietyId = society.societyId.toInt()
                notifyDataSetChanged()
                callback.invoke(society)
            }
        }
    }

    fun setCurrentSocietyId(societyId: Int) {
        selectedSocietyId = societyId
    }

    fun updateList(societies: List<Society>) {
        societyList = societies
        notifyDataSetChanged()
    }
}