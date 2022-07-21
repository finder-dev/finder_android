package com.finder.android.mbti.list.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.dataobj.CommunityHotTitleData

class CommunityHotListAdapter(
    val context: Context,
    private val dataList: ObservableArrayList<CommunityHotTitleData>
) : RecyclerView.Adapter<CommunityHotListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityHotListViewHolder {
        return CommunityHotListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_home_hot_community,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunityHotListViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int = dataList.size
}