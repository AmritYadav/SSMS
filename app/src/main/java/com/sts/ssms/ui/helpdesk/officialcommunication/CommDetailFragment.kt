package com.sts.ssms.ui.helpdesk.officialcommunication

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.databinding.FragmentCommDetailsBinding
import com.sts.ssms.ui.helpdesk.officialcommunication.adapters.CommCommentAdapter
import com.sts.ssms.ui.helpdesk.officialcommunication.models.Communication
import com.sts.ssms.ui.helpdesk.officialcommunication.viewmodels.CommDetailsViewModel
import com.sts.ssms.utils.afterTextChanged
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.visibleGone
import kotlinx.android.synthetic.main.layout_comm_comment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommDetailFragment : Fragment() {

    private val viewModel by viewModel<CommDetailsViewModel>()

    private lateinit var binding: FragmentCommDetailsBinding

    private lateinit var commentAdapter: CommCommentAdapter

    companion object {
        const val KEY_COMM_MODEL = "COMM_MODEL"
        fun newInstance(bundle: Bundle) = CommDetailFragment().apply {
            arguments = bundle
        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Prevent user interaction with the Android Back Button.
            // Allowing only Up Indicator to navigate back to the source screen
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.onBackPressedDispatcher?.addCallback(backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_notification)?.let {
            it.isVisible = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val comm = arguments?.getParcelable<Communication>(KEY_COMM_MODEL)
        comm?.let {
            binding.comm = it
            viewModel.communication(it.id)
            setupObservers(comm)
            setupRecycler()
        }
        et_comment.afterTextChanged {
            viewModel.commentFormDataChange(et_comment.text.toString())
        }
        comment.setOnClickListener {
            comm?.id?.let { viewModel.comment(it, et_comment.text.toString()) }
            et_comment.text.clear()
        }
    }

    private fun setupObservers(letter: Communication) {
        viewModel.commentForm.observe(viewLifecycleOwner) {
            comment.isEnabled = it
        }
        viewModel.commentResult.observe(viewLifecycleOwner) {
            if (!it.success.isNullOrEmpty())
                viewModel.communication(letter.id)
            updateUserView(it.success ?: it.error ?: "")
        }
        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> progress_comm_comments.visibleGone(true)
                else -> {
                    progress_comm_comments.visibleGone(false)
                    if (!it.msg.isNullOrEmpty()) updateUserView(it.msg)
                }
            }
        }
        viewModel.communicationsResult.observe(viewLifecycleOwner) {
            if (!it.error.isNullOrEmpty()) {
                updateUserView(it.error)
                return@observe
            }
            if (it.communication != null){
                binding.comm = it.communication
                commentAdapter.submitList(it.communication.comments)
            }
        }
    }

    private fun setupRecycler() {
        commentAdapter = CommCommentAdapter(mutableListOf())
        rv_comments.apply {
            adapter = commentAdapter
        }
    }

    private fun updateUserView(msg: String) = activity?.showToast(msg)

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }
}
