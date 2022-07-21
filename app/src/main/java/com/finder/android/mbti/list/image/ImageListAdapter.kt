package com.finder.android.mbti.list.image

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.R

class ImageListAdapter(val context : Context, val dataList : ObservableArrayList<String>, val mode : Int) :RecyclerView.Adapter<ImageListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        return ImageListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bind(dataList[position], mode)
    }

    override fun getItemCount(): Int = dataList.size
}