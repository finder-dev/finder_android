package com.finder.android.mbti.list.debate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.dataobj.DebateListVO

class DebateListAdapter(val context: Context, val dataList : ObservableArrayList<DebateListVO>) : RecyclerView.Adapter<DebateListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebateListViewHolder {
        return DebateListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_debate_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DebateListViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int =dataList.size
}