package com.sts.ssms.ui.helpdesk.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.conversations.viewmodel.NewPostViewModel
import com.sts.ssms.ui.helpdesk.conversations.viewmodel.PostViewModel
import com.sts.ssms.utils.afterTextChanged
import com.sts.ssms.utils.showToast
import kotlinx.android.synthetic.main.dialog_fragment_new_conversation.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPostFragment : DialogFragment() {

    private val newPostViewModel by viewModel<NewPostViewModel>()
    private val sharedPostViewModel by lazy { requireParentFragment().getViewModel<PostViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_new_conversation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newPostViewModel.newPostFormState.observe(viewLifecycleOwner) {
            btn_new_conversation_post.isEnabled = it.isDataValid
            if (it.titleError != null)
                til_new_conversation_title.error = getString(it.titleError)
            else
                til_new_conversation_title.error = null
            if (it.descriptionError != null)
                til_new_conversation_desc.error = getString(it.descriptionError)
            else
                til_new_conversation_desc.error = null
        }

        newPostViewModel.postResult.observe(viewLifecycleOwner) {
            if (it.success != null) {
                updateUser(it.success)
                sharedPostViewModel.loadNewConversation.value = true
                dialog?.dismiss()
            } else {
                updateUser(it.error!!)
            }
        }

        ti_et_new_conversation_title.afterTextChanged {
            newPostViewModel.newPostFormState(
                ti_et_new_conversation_title.text.toString(),
                ti_et_new_conversation_desc.text.toString()
            )
        }

        ti_et_new_conversation_desc.afterTextChanged {
            newPostViewModel.newPostFormState(
                ti_et_new_conversation_title.text.toString(),
                ti_et_new_conversation_desc.text.toString()
            )
        }

        btn_new_conversation_post.setOnClickListener {
            newPostViewModel.postNewConversation(
                ti_et_new_conversation_title.text.toString(),
                ti_et_new_conversation_desc.text.toString()
            )
        }
    }

    private fun updateUser(message: String) {
        activity?.showToast(message)
    }
}
