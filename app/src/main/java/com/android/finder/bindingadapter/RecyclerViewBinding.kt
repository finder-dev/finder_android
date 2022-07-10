package com.android.finder.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.dataobj.Content
import com.android.finder.list.community.CommunityListAdapter

object RecyclerViewBinding {

    @BindingAdapter("communityAdapter")
    @JvmStatic
    fun RecyclerView.setCommunityAdapter(list : ObservableArrayList<Content>) {
        if(this.adapter == null) {
            adapter = CommunityListAdapter(this.context, list)
        }
        adapter?.notifyDataSetChanged()
    }
}