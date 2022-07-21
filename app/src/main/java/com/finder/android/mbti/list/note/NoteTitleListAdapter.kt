package com.finder.android.mbti.list.note

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.dataobj.NoteListData

class NoteTitleListAdapter(val context: Context, val dataList : ObservableArrayList<NoteListData>) : RecyclerView.Adapter<NoteTitleListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTitleListViewHolder {
        return NoteTitleListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_note_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteTitleListViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}