package com.android.finder.list.image

import androidx.recyclerview.widget.RecyclerView
import com.android.finder.databinding.ItemImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.io.File

class ImageListViewHolder(val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrl : File) {
        Glide.with(binding.root)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .transform(CenterCrop(), RoundedCorners(4))
            .into(binding.imageView)

        binding.deleteButton.bringToFront()
        binding.deleteButton.setOnClickListener {
            //제거 이벤트
        }
    }
}