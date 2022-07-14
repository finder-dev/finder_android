package com.android.finder.list.community

import androidx.recyclerview.widget.RecyclerView
import com.android.finder.CommentAttributeClickEvent
import com.android.finder.ReCommentAttributeClickEvent
import com.android.finder.databinding.ItemRecommentBinding
import com.android.finder.dataobj.ReCommentData
import org.greenrobot.eventbus.EventBus

class CommunityDetailReCommentViewHolder(val binding : ItemRecommentBinding) : RecyclerView.ViewHolder(binding.root)  {

    fun bind(item : ReCommentData) {
        binding.contentTextView.text = item.replyContent
        binding.postDateView.text = item.createTime
        binding.postUserMbtiView.text = item.userMBTI
        binding.userNicknameView.text = item.userNickname
        binding.attributeButton.setOnClickListener {
            EventBus.getDefault().post(ReCommentAttributeClickEvent(item))
        }
    }
}