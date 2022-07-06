package com.sts.ssms.ui.helpdesk.photogallery.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemPhotoBinding
import com.sts.ssms.ui.helpdesk.photogallery.model.PhotoUiModel
import com.sts.ssms.utils.inflateDataBindingView

class PhotoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        photoUiModel: PhotoUiModel,
        callback: (pos: Int) -> Unit
    ) {
        binding.photoUiModel = photoUiModel
        itemView.setOnClickListener { callback.invoke(adapterPosition) }
    }

    companion object {
        fun create(parent: ViewGroup): PhotoViewHolder =
            PhotoViewHolder(parent.inflateDataBindingView(R.layout.item_photo))
    }
}