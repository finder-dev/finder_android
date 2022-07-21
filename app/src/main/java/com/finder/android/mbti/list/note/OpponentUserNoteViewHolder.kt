package com.finder.android.mbti.list.note

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.databinding.ItemOpponentNoteBinding
import com.finder.android.mbti.dataobj.NoteWithUserVO
import com.finder.android.mbti.dataobj.UserNoteVO

class OpponentUserNoteViewHolder(val binding : ItemOpponentNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : UserNoteVO) {
        binding.contentTextView.text = item.content
        binding.createTimeView.text = item.createTime
    }
}