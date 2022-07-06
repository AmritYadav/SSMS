package com.sts.ssms.ui.helpdesk.myflat.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.myflat.model.MyFlatUiModel
import com.sts.ssms.utils.formatString
import com.sts.ssms.utils.inflateView
import com.sts.ssms.utils.loadCircularImage
import com.sts.ssms.utils.visibleGone

class MyFlatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val container = itemView.findViewById<View>(R.id.container_flat_member)
    private val flatMemberDetails = itemView.findViewById<View>(R.id.flat_member_details)

    private val memberPhoto = itemView.findViewById<ImageView>(R.id.iv_flat_member_avatar)
    private val ownerName = itemView.findViewById<TextView>(R.id.tv_flat_member_name)
    private val dob = itemView.findViewById<TextView>(R.id.tv_flat_member_dob)
    private val contactNo = itemView.findViewById<TextView>(R.id.tv_flat_member_contact)
    private val grpHideFields = itemView.findViewById<Group>(R.id.grp)
    private val relation = itemView.findViewById<TextView>(R.id.tv_flat_member_relation_val)
    private val aadhaar = itemView.findViewById<TextView>(R.id.tv_flat_member_aadhaar_val)
    private val pan = itemView.findViewById<TextView>(R.id.tv_flat_member_pan_val)
    private val voter = itemView.findViewById<TextView>(R.id.tv_flat_member_voter_val)
    private val email = itemView.findViewById<TextView>(R.id.tv_flat_member_email_val)
    private val associate = itemView.findViewById<TextView>(R.id.tv_flat_member_associate_val)
    private val photoApproval =
        itemView.findViewById<TextView>(R.id.tv_flat_member_photo_approval_val)

    fun bind(flatMember: MyFlatUiModel, imageCallback: (imgUrl: String?) -> Unit, callback: () -> Unit) {

        flatMemberDetails.visibleGone(flatMember.isExpanded)

        ownerName.text = flatMember.ownerName
        dob.formatString(R.string.flat_member_dob, flatMember.dob)
        contactNo.formatString(R.string.flat_member_contact_no, flatMember.contactNo)
        relation.text = flatMember.relation

        memberPhoto.loadCircularImage(flatMember.avatar)
        memberPhoto.setOnClickListener { imageCallback.invoke(flatMember.avatar) }

        grpHideFields.visibleGone(flatMember.isOwner)

        aadhaar.text = flatMember.aadhaarCard
        pan.text = flatMember.panCard
        voter.text = flatMember.voterId
        email.text = flatMember.email
        associate.text = if (flatMember.isAssociateMember) "Associate Member" else "Member"
        photoApproval.text = flatMember.photoStatus
        container.setOnClickListener {
            callback.invoke()
        }
    }

    companion object {
        fun create(parent: ViewGroup): MyFlatViewHolder {
            return MyFlatViewHolder(parent.inflateView(R.layout.item_flat_member))
        }
    }
}