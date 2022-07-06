package com.sts.ssms.ui.helpdesk.noticeboard.adapters

import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.databinding.ItemNoticeBinding
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import com.sts.ssms.utils.inflateDataBindingView

class NoticeViewHolder(private val binding: ItemNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        notice: Notice?,
        itemClickCallback: (notice: Notice) -> Unit
    ) {
        notice?.let {
            binding.descBody = HtmlCompat.fromHtml(
                it.desc.substringAfter("<body>"),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ).toString().replace("\n", " ")
            binding.notice = it
        }
        itemView.setOnClickListener { notice?.let { obj -> itemClickCallback.invoke(obj) } }
    }

    fun bind(
        notice: Notice?,
        searchItemClickCallback: SearchItemClickCallback<Notice>
    ) {
        notice?.let {
            binding.descBody = HtmlCompat.fromHtml(it.desc.substringAfter("<body>"),
                HtmlCompat.FROM_HTML_MODE_COMPACT).toString().replace("\n", " ")
            binding.notice = it
        }
        itemView.setOnClickListener { notice?.let { searchItemClickCallback.onClick(notice) } }
    }

    companion object {
        fun create(parent: ViewGroup): NoticeViewHolder = NoticeViewHolder(
            parent.inflateDataBindingView(R.layout.item_notice)
        )
    }
}