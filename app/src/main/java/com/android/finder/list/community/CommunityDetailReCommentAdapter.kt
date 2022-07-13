package com.android.finder.list.community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.R
import com.android.finder.dataobj.CommentData
import com.android.finder.dataobj.ReCommentData

class CommunityDetailReCommentAdapter(val context: Context, private val dataList: List<ReCommentData>) :
    RecyclerView.Adapter<CommunityDetailReCommentViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityDetailReCommentViewHolder {
        return CommunityDetailReCommentViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_recomment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunityDetailReCommentViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}