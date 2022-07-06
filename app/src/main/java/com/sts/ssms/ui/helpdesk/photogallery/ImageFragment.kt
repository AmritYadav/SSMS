package com.sts.ssms.ui.helpdesk.photogallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.sts.ssms.R
import com.sts.ssms.databinding.FragmentImageBinding
import com.sts.ssms.ui.helpdesk.photogallery.model.ImageModel
import com.sts.ssms.utils.loadOriginalImage
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment() {

    companion object {
        private const val KEY_IMAGE_MODEL = "IMAGE_MODEL"
        fun newInstance(imageModel: ImageModel): ImageFragment = ImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_IMAGE_MODEL, imageModel)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImageBinding.inflate(inflater)
        val imageModel = arguments?.getParcelable<ImageModel>(KEY_IMAGE_MODEL)
        imageModel?.let {
            binding.imageModel = it
        }
        return binding.root
    }


}
