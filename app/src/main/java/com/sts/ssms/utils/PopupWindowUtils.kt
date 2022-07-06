package com.sts.ssms.utils

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.widget.ListPopupWindow
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.amenity.model.AmenityCategory
import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyBuilding
import com.sts.ssms.ui.helpdesk.noticeboard.models.NoticeType

fun View.showListPopupWindow(flatList: List<Flat>, callBack: (itemId: Int) -> Unit) {
    val listPopupWindow = ListPopupWindow(this.context)
    val adapter =
        ArrayAdapter<Flat>(this.context, R.layout.simple_popup_item, flatList)
    listPopupWindow.setAdapter(adapter)
    listPopupWindow.height = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.width = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.isModal = true
    listPopupWindow.anchorView = this
    listPopupWindow.setOnItemClickListener { _, _, pos, _ ->
        val flat = flatList[pos]
        if (this is Button) this.text = flat.display
        callBack.invoke(flat.flatId)
        listPopupWindow.dismiss()
    }
    listPopupWindow.show()
}

fun View.showPopupMenuFilterTickets(callBack: (itemId: Int) -> Unit) {
    val popup = PopupMenu(this.context, this)
    popup.menuInflater.inflate(R.menu.menu_filter_ticket, popup.menu)
    popup.setOnMenuItemClickListener {
        callBack.invoke(it.itemId)
        true
    }
    popup.show()
}

fun View.showNoticePopupWindow(noticeTypes: List<NoticeType>, callBack: (itemId: Int) -> Unit) {
    val listPopupWindow = ListPopupWindow(this.context)
    val adapter =
        ArrayAdapter<NoticeType>(this.context, R.layout.simple_popup_item, noticeTypes)
    listPopupWindow.setAdapter(adapter)
    listPopupWindow.height = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.width = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.isModal = true
    listPopupWindow.anchorView = this
    listPopupWindow.setOnItemClickListener { _, _, pos, _ ->
        val noticeType = noticeTypes[pos]
        if (this is Button) this.text = noticeType.name
        callBack.invoke(noticeType.id)
        listPopupWindow.dismiss()
    }
    listPopupWindow.show()
}

fun View.showSocietyBuildingPopupWindow(
    societiesBuildings: List<SocietyBuilding>, callBack: (itemId: String) -> Unit
) {
    val listPopupWindow = ListPopupWindow(this.context)
    val adapter =
        ArrayAdapter<SocietyBuilding>(this.context, R.layout.simple_popup_item, societiesBuildings)
    listPopupWindow.setAdapter(adapter)
    listPopupWindow.height = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.width = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.isModal = true
    listPopupWindow.anchorView = this
    listPopupWindow.setOnItemClickListener { _, _, pos, _ ->
        val societyBuilding = societiesBuildings[pos]
        if (this is Button) this.text = societyBuilding.name
        callBack.invoke(societyBuilding.id)
        listPopupWindow.dismiss()
    }
    listPopupWindow.show()
}

fun View.showDocumentsPopupWindow(flats: List<Flat>, callBack: (flat: Flat) -> Unit) {
    val listPopupWindow = ListPopupWindow(this.context)
    val adapter =
        ArrayAdapter<Flat>(this.context, R.layout.simple_popup_item, flats)
    listPopupWindow.setAdapter(adapter)
    listPopupWindow.height = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.width = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.isModal = true
    listPopupWindow.anchorView = this
    listPopupWindow.setOnItemClickListener { _, _, pos, _ ->
        val flat = flats[pos]
        if (this is Button) this.text = flat.display
        callBack.invoke(flat)
        listPopupWindow.dismiss()
    }
    listPopupWindow.show()
}

fun View.showAmenityCategoryPopupWindow(
    categories: List<AmenityCategory>,
    callBack: (category: AmenityCategory) -> Unit
) {
    val listPopupWindow = ListPopupWindow(this.context)
    val adapter =
        ArrayAdapter<AmenityCategory>(this.context, R.layout.simple_popup_item, categories)
    listPopupWindow.setAdapter(adapter)
    listPopupWindow.height = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.width = ListPopupWindow.WRAP_CONTENT
    listPopupWindow.isModal = true
    listPopupWindow.anchorView = this
    listPopupWindow.setOnItemClickListener { _, _, pos, _ ->
        val category = categories[pos]
        if (this is Button) this.text = category.displayName
        callBack.invoke(category)
        listPopupWindow.dismiss()
    }
    listPopupWindow.show()
}
