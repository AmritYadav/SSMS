package com.sts.ssms.ui.helpdesk.photogallery.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sts.ssms.ui.helpdesk.photogallery.ImageFragment
import com.sts.ssms.ui.helpdesk.photogallery.model.ImageModel

class ImagePagerAdapter(fa: FragmentActivity, private val imgList: List<ImageModel>) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = imgList.size

    override fun createFragment(position: Int): Fragment =
        ImageFragment.newInstance(imgList[position])

}