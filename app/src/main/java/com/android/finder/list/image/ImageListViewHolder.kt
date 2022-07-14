package com.android.finder.list.image

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.ImageDeleteEvent
import com.android.finder.databinding.ItemImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.greenrobot.eventbus.EventBus
import java.io.File

class ImageListViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrl: String, mode: Int) {
        Glide.with(binding.root)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .transform(CenterCrop(), RoundedCorners(4))
            .into(binding.imageView)

        //mode가 0이면 x가 안보임, 1이면 수정모드라 보임
        binding.deleteButton.isVisible = (mode == 1)
        binding.deleteButton.bringToFront()
        binding.deleteButton.setOnClickListener {
            //제거 이벤트
            EventBus.getDefault().post(ImageDeleteEvent(imageUrl))
        }
    }
}