package com.finder.android.mbti.list.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.database.DataBaseUtil
import com.finder.android.mbti.database.entity.SearchWordEntity

class SearchWordListAdapter(
    val context: Context,
    val dataList: ObservableArrayList<SearchWordEntity>
) : RecyclerView.Adapter<SearchWordListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchWordListViewHolder {
        return SearchWordListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_search_word,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchWordListViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.binding.closeButton.setOnClickListener {
            DataBaseUtil.deleteWordData(dataList[position])
            dataList.removeAt(position)
        }
    }

    override fun getItemCount(): Int = dataList.size
}