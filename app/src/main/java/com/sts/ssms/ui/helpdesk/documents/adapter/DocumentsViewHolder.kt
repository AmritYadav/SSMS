package com.sts.ssms.ui.helpdesk.documents.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemDocumentBinding
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import com.sts.ssms.utils.inflateDataBindingView

class DocumentsViewHolder(private val binding: ItemDocumentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        document: Documents?,
        callback: (docUrl: String) -> Unit
    ) {
        document?.let { binding.document = it }
        binding.downloadDocument.setOnClickListener {
            document?.let { obj -> callback.invoke(obj.path) }
        }
    }

    fun bind(document: Documents?) {
        document?.let { binding.document = it }
    }

    companion object {
        fun create(parent: ViewGroup): DocumentsViewHolder = DocumentsViewHolder(
            parent.inflateDataBindingView(R.layout.item_document)
        )
    }

}