package com.finder.android.mbti.list.home

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.databinding.ItemHomeHotCommunityBinding
import com.finder.android.mbti.MoveToCommunityDetail
import com.finder.android.mbti.dataobj.CommunityHotTitleData
import org.greenrobot.eventbus.EventBus

class CommunityHotListViewHolder(val binding: ItemHomeHotCommunityBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : CommunityHotTitleData, position : Int) {
        binding.titleTextView.text = item.title
        binding.indexView.text = (position+1).toString()
        binding.root.setOnClickListener {
            EventBus.getDefault().post(MoveToCommunityDetail(item.communityId))
        }
    }
}