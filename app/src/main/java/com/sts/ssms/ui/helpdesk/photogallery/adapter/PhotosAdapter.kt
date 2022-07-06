package com.sts.ssms.ui.helpdesk.photogallery.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.ui.helpdesk.photogallery.model.ImageModel
import com.sts.ssms.ui.helpdesk.photogallery.model.PhotoUiModel
import com.sts.ssms.ui.helpdesk.photogallery.model.toImageModel

class PhotosAdapter(private var photos: List<PhotoUiModel>, val callback: (pos: Int) -> Unit) :
    RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder.create(parent)

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) =
        holder.bind(photos[position], callback)

    fun updatePhotos(newPhotos: List<PhotoUiModel>) {
        photos = newPhotos
        notifyDataSetChanged()
    }

    fun getImageModels(): List<ImageModel> = photos.map { it.toImageModel() }
}