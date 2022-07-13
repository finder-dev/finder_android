package com.android.finder.list.community

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.dataobj.CommentData
import com.android.finder.R

class CommunityDetailCommentAdapter(val context: Context, private val dataList: ObservableArrayList<CommentData>) :
    RecyclerView.Adapter<CommunityDetailCommentViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityDetailCommentViewHolder {
        return CommunityDetailCommentViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_comment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunityDetailCommentViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

}