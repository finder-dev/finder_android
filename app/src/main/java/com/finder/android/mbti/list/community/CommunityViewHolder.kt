package com.finder.android.mbti.list.community

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.ItemCommunityPostBinding
import com.finder.android.mbti.dataobj.Content
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.finder.android.mbti.App
import com.finder.android.mbti.LikeCommunityContent
import com.finder.android.mbti.MoveToCommunityDetail
import org.greenrobot.eventbus.EventBus

class CommunityViewHolder(val binding: ItemCommunityPostBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Content) {
        binding.apply {
            questionMbtiView.text = item.communityMBTI

            isCuriousImageView.visibility = if(item.isQuestion)View.VISIBLE else View.INVISIBLE
            communityTitleTextView.text = item.communityTitle
            communityContentsTextView.text = item.communityContent
            includePublisherData.postUserMbtiView.text = item.userMBTI
            includePublisherData.postDateView.text = item.createTime
            includePublisherData.userNicknameView.text = item.userNickname
            Glide.with(binding.root)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .transform(RoundedCorners(4))
                .into(binding.previewImage)
            binding.previewImage.isVisible = item.imageUrl != null
            likeCountView.text = item.likeCount.toString()
            commentCountView.text = item.answerCount.toString()
            likeButton.setColorFilter(
                if (item.likeUser) App.instance.resources.getColor(
                    R.color.mainColor,
                    null
                ) else App.instance.resources.getColor(R.color.black7, null)
            )
            binding.likeButton.setOnClickListener {
                EventBus.getDefault().post(
                    LikeCommunityContent(
                        item,
                        adapterPosition
                    )
                )
            }
            binding.root.setOnClickListener {
                EventBus.getDefault().post(MoveToCommunityDetail(item.communityId))
            }
        }
    }
}