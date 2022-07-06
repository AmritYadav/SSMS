package com.sts.ssms.ui.helpdesk.profile.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.data.society.model.Society
import com.sts.ssms.utils.inflateView

class SocietyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val checkableSociety = itemView.findViewById<CheckedTextView>(R.id.ctv_society)

    fun bind(society: Society, isChecked: Boolean) {
        checkableSociety.text = society.name
        checkableSociety.isChecked = isChecked
    }

    companion object {
        fun create(parent: ViewGroup): SocietyViewHolder =
            SocietyViewHolder(parent.inflateView(R.layout.item_society))
    }
}