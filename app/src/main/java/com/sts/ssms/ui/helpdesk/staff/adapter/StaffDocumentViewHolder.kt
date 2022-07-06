package com.sts.ssms.ui.helpdesk.staff.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.staff.api.Document
import com.sts.ssms.utils.inflateView

class StaffDocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val docName = itemView.findViewById<TextView>(R.id.tv_staff_details_doc_name)
    private val downloadDoc = itemView.findViewById<ImageView>(R.id.iv_staff_doc_download)

    fun bind(document: Document, downloadCallback: (url: String?) -> Unit) {
        if (!document.path.isNullOrEmpty()) {
            val fileName = document.path.substring(document.path.lastIndexOf("/") + 1)
            docName.text = fileName
        }
        downloadDoc.setOnClickListener { downloadCallback.invoke(document.path) }
    }

    companion object {
        fun create(parent: ViewGroup): StaffDocumentViewHolder = StaffDocumentViewHolder(
            parent.inflateView(R.layout.item_staff_details_document)
        )
    }
}