package com.finder.android.mbti.list.note

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.databinding.ItemMyNoteBinding
import com.finder.android.mbti.dataobj.UserNoteVO

class MyUserNoteViewHolder(val binding: ItemMyNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserNoteVO) {
        binding.contentTextView.text = item.content
        binding.createTimeView.text = item.createTime
    }
}