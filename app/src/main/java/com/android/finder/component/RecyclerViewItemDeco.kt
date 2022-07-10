package com.android.finder.component

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.dpToPx

class RecyclerViewItemDeco(private val context : Context, private val height : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if(parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?:0 - 1) {
            outRect.bottom = height.dpToPx(context)
        }
    }
}