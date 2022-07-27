package com.finder.android.mbti.list.home

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.ClickSearchWordEvent
import com.finder.android.mbti.database.DataBaseUtil
import com.finder.android.mbti.database.entity.SearchWordEntity
import com.finder.android.mbti.databinding.ItemSearchWordBinding
import org.greenrobot.eventbus.EventBus

class SearchWordListViewHolder(val binding : ItemSearchWordBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item : SearchWordEntity) {
        binding.wordView.text = item.searchWord
        binding.root.setOnClickListener {
            EventBus.getDefault().post(ClickSearchWordEvent(item))
        }
    }
}