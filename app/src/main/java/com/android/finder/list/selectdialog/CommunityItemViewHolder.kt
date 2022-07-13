package com.android.finder.list.selectdialog

import androidx.recyclerview.widget.RecyclerView
import com.android.finder.R
import com.android.finder.databinding.ItemTextBinding
import com.android.finder.dataobj.TextListItem
import com.android.finder.setTextColorResource

class CommunityItemViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title : String) {
        binding.itemNameView.text = title
    }
}