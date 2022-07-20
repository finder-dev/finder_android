package com.finder.android.mbti.list.note

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.databinding.ItemNoteListBinding
import com.finder.android.mbti.dataobj.NoteListData

class NoteTitleListViewHolder(val binding : ItemNoteListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : NoteListData) {
        binding.userNicknameTextView.text = item.userNickname
        binding.lastNoteTextView.text = item.content
        binding.sendTimeView.text = item.createTime
        binding.root.setOnClickListener {

        }
    }
}