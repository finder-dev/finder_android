package com.finder.android.mbti.list.selectdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.result.StringResult

class CommunityItemAdapter(val context: Context, private val dataList: ArrayList<String>,val  result : StringResult) :
    RecyclerView.Adapter<CommunityItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityItemViewHolder {
        return CommunityItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_text,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunityItemViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.binding.root.setOnClickListener {
            result.finish(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size
}