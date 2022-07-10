package com.android.finder.list.griddialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.R
import com.android.finder.dataobj.TextListItem

class GridSelectDialogItemAdapter(val context: Context, val dataList: List<TextListItem>) :
    RecyclerView.Adapter<GridSelectDialogItemViewHolder>() {

    var currentSelectItem : String? = null
    var currentPosition : Int = -1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridSelectDialogItemViewHolder {
        return GridSelectDialogItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_text,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GridSelectDialogItemViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.binding.itemNameView.setOnClickListener {
            if(currentPosition >= 0 && currentPosition < dataList.size)dataList[currentPosition].isSelected = false
            currentSelectItem = dataList[position].item
            dataList[position].isSelected = !dataList[position].isSelected
            currentPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = dataList.size
}