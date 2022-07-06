package com.sts.ssms.ui.helpdesk.features

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.R
import kotlinx.android.synthetic.main.fragment_feature.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FeatureFragment : Fragment() {

    private val featuresViewModel by viewModel<FeaturesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feature, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        featuresViewModel.ssmsFeatures()

        featuresViewModel.features.observe(viewLifecycleOwner) {
            wv_features.apply {
                loadDataWithBaseURL(null, it, "text/html", "utf-8", null)
                setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }
}
