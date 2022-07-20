package com.finder.android.mbti.list.community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.dataobj.Content

class CommunityListAdapter(
    val context: Context,
    private val dataList: ObservableArrayList<Content>
) : RecyclerView.Adapter<CommunityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        return CommunityViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_community_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}