package com.finder.android.mbti.list.image

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.finder.android.mbti.databinding.ItemPhotoImageBinding

class ImageDetailViewPagerViewHolder(val binding : ItemPhotoImageBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        Glide.with(binding.root)
            .load(item)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .transform(RoundedCorners(4))
            .into(binding.photoView)
    }
}