package com.finder.android.mbti.list.community

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.databinding.ItemCommentBinding
import com.finder.android.mbti.CommentAttributeClickEvent
import com.finder.android.mbti.dataobj.CommentData
import org.greenrobot.eventbus.EventBus

class CommunityDetailCommentViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : CommentData) {
        binding.contentTextView.text = item.answerContent
        binding.postDateView.text = item.createTime
        binding.postUserMbtiView.text = item.userMBTI
        binding.userNicknameView.text = item.userNickname
        binding.reCommentRecyclerView.adapter = CommunityDetailReCommentAdapter(
            binding.root.context,
            item.replyList
        )
        binding.attributeButton.setOnClickListener {
            EventBus.getDefault().post(CommentAttributeClickEvent(item))
        }
        binding.reCommentRecyclerView.adapter?.notifyDataSetChanged()

    }
}