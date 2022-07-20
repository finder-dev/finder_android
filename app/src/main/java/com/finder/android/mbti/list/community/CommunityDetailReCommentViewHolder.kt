package com.finder.android.mbti.list.community

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.databinding.ItemRecommentBinding
import com.finder.android.mbti.ReCommentAttributeClickEvent
import com.finder.android.mbti.dataobj.ReCommentData
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