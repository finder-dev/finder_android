package com.finder.android.mbti.list.debate

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.ItemDebatePostBinding
import com.finder.android.mbti.App
import com.finder.android.mbti.MoveToDebateDetail
import com.finder.android.mbti.dataobj.DebateListVO
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