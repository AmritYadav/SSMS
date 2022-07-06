package com.sts.ssms.ui.helpdesk.myflat.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.SimpleItemAnimator
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.myflat.MyFlatViewModel
import com.sts.ssms.ui.helpdesk.myflat.adapter.MyFlatAdapter
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.fragment_flat_members.*
import kotlinx.android.synthetic.main.layout_empty_state.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class FlatOwnersFragment : Fragment() {

    private lateinit var flatOwnerAdapter: MyFlatAdapter
    private val myFlatViewModel by lazy { requireParentFragment().getViewModel<MyFlatViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flat_members, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.formatString(R.string.flat_empty_msg, "Owner(s)")

        setupAdapter()
        myFlatViewModel.flatOwnerResult.observe(viewLifecycleOwner) { flatList ->
            flatOwnerAdapter.submitList(flatList)
            flat_member_empty_state.visibleGone(flatList.isEmpty())
        }
    }

    private fun setupAdapter() {
        flatOwnerAdapter = MyFlatAdapter(ArrayList()) { imgUrl -> loadFullSizeImage(imgUrl) }
        rv_flat_members.apply {
            adapter = flatOwnerAdapter
            setHasFixedSize(true)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun loadFullSizeImage(imgUrl: String?) {
        if (imgUrl.isNullOrEmpty()) {
            activity?.showToast("No image found")
            return
        }
        activity?.let {
            val appCompatActivity = activity as AppCompatActivity
            appCompatActivity.showImageDialog(imgUrl)
        }
    }
}
