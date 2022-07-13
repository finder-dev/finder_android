package com.android.finder.list.community

import androidx.recyclerview.widget.RecyclerView
import com.android.finder.component.RecyclerViewItemDeco
import com.android.finder.databinding.ItemCommentBinding
import com.android.finder.dataobj.CommentData

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
        binding.reCommentRecyclerView.adapter?.notifyDataSetChanged()

    }
}