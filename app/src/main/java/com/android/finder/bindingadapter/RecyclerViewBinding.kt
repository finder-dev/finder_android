package com.android.finder.bindingadapter

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.dataobj.Content
import com.android.finder.list.community.CommunityListAdapter
import com.android.finder.list.image.ImageListAdapter
import java.io.File

object RecyclerViewBinding {

    @BindingAdapter("communityAdapter")
    @JvmStatic
    fun RecyclerView.setCommunityAdapter(list : ObservableArrayList<Content>) {
        if(this.adapter == null) {
            adapter = CommunityListAdapter(this.context, list)
        }
        adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("imageAdapter")
    @JvmStatic
    fun RecyclerView.setImageAdapter(list : ObservableArrayList<File>) {
        if(this.adapter == null) {
            adapter = ImageListAdapter(this.context, list)
        }
        adapter?.notifyDataSetChanged()
    }
}