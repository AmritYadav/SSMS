package com.sts.ssms.ui.helpdesk.photogallery

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.photogallery.adapter.ImagePagerAdapter
import com.sts.ssms.ui.helpdesk.photogallery.model.ImageModel

class ImagePagerFragment : DialogFragment() {

    private lateinit var viewPager: ViewPager2

    companion object {
        private const val KEY_IMAGES = "KEY_IMAGES"
        private const val KEY_IMAGE_POSITION = "IMAGE_POSITION"
        fun newInstance(images: List<ImageModel>, position: Int): ImagePagerFragment =
            ImagePagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_IMAGES, images as ArrayList)
                    putInt(KEY_IMAGE_POSITION, position)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPager = inflater.inflate(R.layout.fragment_image_pager, container, false) as ViewPager2

        arguments?.getParcelableArrayList<ImageModel>(KEY_IMAGES)?.let {
            viewPager.apply {
                adapter = activity?.let { fa -> ImagePagerAdapter(fa, it) }
            }
        }
        arguments?.getInt(KEY_IMAGE_POSITION, 0)?.let {
            viewPager.setCurrentItem(it, false)
        }

        return viewPager
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}
