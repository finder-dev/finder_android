package com.finder.android.mbti.list.note

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R
import com.finder.android.mbti.caching.CachingData
import com.finder.android.mbti.dataobj.NoteWithUserVO
import com.finder.android.mbti.dataobj.UserNoteVO

class UserNoteListAdapter(val context: Context, val dataList : ObservableArrayList<UserNoteVO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1) {
            MyUserNoteViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.item_my_note,
                    parent,
                    false
                )
            )
        } else {
            OpponentUserNoteViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.item_opponent_note,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataList[position].isMynote) 1 else 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(dataList[position].isMynote) {
            (holder as MyUserNoteViewHolder).bind(dataList[position])
        } else (holder as OpponentUserNoteViewHolder).bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}