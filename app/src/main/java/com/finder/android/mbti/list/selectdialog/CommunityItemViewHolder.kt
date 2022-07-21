package com.finder.android.mbti.list.selectdialog

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.databinding.ItemTextBinding

class CommunityItemViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title : String) {
        binding.itemNameView.text = title
    }
}