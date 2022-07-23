package com.finder.android.mbti.list.image

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.finder.android.mbti.R

class ImageDetailViewPagerAdapter(val context: Context, val dataList: Array<String>) : RecyclerView.Adapter<ImageDetailViewPagerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageDetailViewPagerViewHolder {
        return ImageDetailViewPagerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_photo_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageDetailViewPagerViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

}