package com.android.finder.list.griddialog

import androidx.recyclerview.widget.RecyclerView
import com.android.finder.R
import com.android.finder.databinding.ItemTextBinding
import com.android.finder.dataobj.TextListItem
import com.android.finder.setTextColorResource

class GridSelectDialogItemViewHolder(val binding: ItemTextBinding) :
    RecyclerView.ViewHolder(binding.root)  {

        fun bind(item : TextListItem) {
            binding.itemNameView.text = item.item
            binding.itemNameView.setTextColorResource(if(item.isSelected) R.color.mainColor else R.color.black04)
        }
}