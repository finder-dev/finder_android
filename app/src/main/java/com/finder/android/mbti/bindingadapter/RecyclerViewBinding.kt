package com.finder.android.mbti.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.bindingadapter.RecyclerViewBinding.setDebateListAdapter
import com.finder.android.mbti.dataobj.*
import com.finder.android.mbti.list.community.CommunityDetailCommentAdapter
import com.finder.android.mbti.list.community.CommunityListAdapter
import com.finder.android.mbti.list.debate.DebateListAdapter
import com.finder.android.mbti.list.home.CommunityHotListAdapter
import com.finder.android.mbti.list.image.ImageListAdapter
import com.finder.android.mbti.list.note.NoteTitleListAdapter

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

    @BindingAdapter("noteListAdapter")
    @JvmStatic
    fun RecyclerView.setNoteListAdapter(list : ObservableArrayList<NoteListData>) {
        if(this.adapter == null) adapter = NoteTitleListAdapter(this.context, list)
        adapter?.notifyDataSetChanged()
    }
}