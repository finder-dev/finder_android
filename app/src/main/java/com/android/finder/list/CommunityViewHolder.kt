package com.android.finder.list

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.App
import com.android.finder.R
import com.android.finder.databinding.ItemCommunityPostBinding
import com.android.finder.dataobj.CommunityListDto
import com.android.finder.dataobj.Content

class CommunityViewHolder(val binding: ItemCommunityPostBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Content) {
        binding.apply {
            questionMbtiView.text = item.communityMBTI
            isCuriousImageView.isVisible = item.isQuestion
            communityTitleTextView.text = item.communityTitle
            communityContentsTextView.text = item.communityContent
            includePublisherData.postUserMbtiView.text = item.userMBTI
            includePublisherData.postDateView.text = item.createTime
            includePublisherData.userNicknameView.text = item.userNickname
            likeCountView.text = item.likeCount.toString()
            commentCountView.text = item.answerCount.toString()
            likeButton.setColorFilter(
                if (item.likeUser) App.instance.resources.getColor(
                    R.color.mainColor,
                    null
                ) else App.instance.resources.getColor(R.color.black7, null)
            )
        }
    }
}