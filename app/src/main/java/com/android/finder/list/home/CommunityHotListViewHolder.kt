package com.android.finder.list.home

import androidx.recyclerview.widget.RecyclerView
import com.android.finder.MoveToCommunityDetail
import com.android.finder.databinding.ItemHomeHotCommunityBinding
import com.android.finder.dataobj.CommunityHotTitleData
import org.greenrobot.eventbus.EventBus

class CommunityHotListViewHolder(val binding: ItemHomeHotCommunityBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : CommunityHotTitleData, position : Int) {
        binding.titleTextView.text = item.title
        binding.indexView.text = position.toString()
        binding.root.setOnClickListener {
            EventBus.getDefault().post(MoveToCommunityDetail(item.communityId))
        }
    }
}