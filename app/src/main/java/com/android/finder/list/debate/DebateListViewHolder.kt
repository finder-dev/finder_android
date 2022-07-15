package com.android.finder.list.debate

import androidx.recyclerview.widget.RecyclerView
import com.android.finder.App
import com.android.finder.MoveToCommunityDetail
import com.android.finder.MoveToDebateDetail
import com.android.finder.R
import com.android.finder.databinding.ItemDebatePostBinding
import com.android.finder.dataobj.DebateListVO
import org.greenrobot.eventbus.EventBus

class DebateListViewHolder(val binding: ItemDebatePostBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DebateListVO) {
        binding.deadlineTextView.text = item.deadline
        binding.debateTitleView.text = item.title
        binding.joinCountTextView.text = App.instance.resources.getString(R.string.join_count_format, item.joinCount.toString())
        binding.root.setOnClickListener {
            EventBus.getDefault().post(MoveToDebateDetail(item.debateId))
        }
    }
}