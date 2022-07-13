package com.android.finder.list.community

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.App
import com.android.finder.LikeCommunityContent
import com.android.finder.MoveToCommunityDetail
import com.android.finder.R
import com.android.finder.databinding.ItemCommunityPostBinding
import com.android.finder.dataobj.Content
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.greenrobot.eventbus.EventBus

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
                EventBus.getDefault().post(LikeCommunityContent(item))
            }
            binding.root.setOnClickListener {
                EventBus.getDefault().post(MoveToCommunityDetail(item.communityId))
            }
        }
    }
}