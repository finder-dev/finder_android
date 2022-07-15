package com.android.finder.bindingadapter

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.bindingadapter.RecyclerViewBinding.setCommentAdapter
import com.android.finder.bindingadapter.RecyclerViewBinding.setHomeCommunityAdapter
import com.android.finder.bindingadapter.RecyclerViewBinding.setImageAdapter
import com.android.finder.dataobj.CommentData
import com.android.finder.dataobj.CommunityHotTitleData
import com.android.finder.dataobj.Content
import com.android.finder.dataobj.DebateListVO
import com.android.finder.list.community.CommunityDetailCommentAdapter
import com.android.finder.list.community.CommunityListAdapter
import com.android.finder.list.debate.DebateListAdapter
import com.android.finder.list.home.CommunityHotListAdapter
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
    fun RecyclerView.setImageAdapter(list : ObservableArrayList<String>) {
        if(this.adapter == null) {
            adapter = ImageListAdapter(this.context, list, 0)
        }
        adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("imageModifyAdapter")
    @JvmStatic
    fun RecyclerView.setImageModifyAdapter(list : ObservableArrayList<String>) {
        if(this.adapter == null) {
            adapter = ImageListAdapter(this.context, list, 1)
        }
        adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("commentAdapter")
    @JvmStatic
    fun RecyclerView.setCommentAdapter(list: ObservableArrayList<CommentData>) {
        if(this.adapter == null) {
            adapter = CommunityDetailCommentAdapter(this.context, list)
        }
        adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("homeCommunityAdapter")
    @JvmStatic
    fun RecyclerView.setHomeCommunityAdapter(list : ObservableArrayList<CommunityHotTitleData>) {
        if(this.adapter == null) adapter = CommunityHotListAdapter(this.context, list)
        adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("debateListAdapter")
    @JvmStatic
    fun RecyclerView.setDebateListAdapter(list : ObservableArrayList<DebateListVO>) {
        if(this.adapter == null) adapter = DebateListAdapter(this.context, list)
        adapter?.notifyDataSetChanged()
    }
}