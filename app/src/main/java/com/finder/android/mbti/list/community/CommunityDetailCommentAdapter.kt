package com.finder.android.mbti.list.community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.dataobj.CommentData
import com.finder.android.mbti.R

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