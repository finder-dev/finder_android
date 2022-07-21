package com.finder.android.mbti.list.griddialog

import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.databinding.ItemTextBinding
import com.finder.android.mbti.dataobj.TextListItem
import com.finder.android.mbti.setTextColorResource

class GridSelectDialogItemViewHolder(val binding: ItemTextBinding) :
    RecyclerView.ViewHolder(binding.root)  {

        fun bind(item : TextListItem) {
            binding.itemNameView.text = item.item
            binding.itemNameView.setTextColorResource(if(item.isSelected) R.color.mainColor else R.color.black04)
        }
}