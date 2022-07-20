package com.finder.android.mbti.component

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finder.android.mbti.dpToPx

class RecyclerViewHorizonItemDeco(
    private val context: Context,
    private val height: Int,
    val isLeftAndRightMargin: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            //4는 cardView에 있어서...
            if (isLeftAndRightMargin) outRect.left = 16.dpToPx(context)
            outRect.right = height.dpToPx(context)
        } else if (parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount ?: 0) - 1) {
            if (isLeftAndRightMargin)outRect.right = 16.dpToPx(context)
        } else {
            outRect.right = height.dpToPx(context)
        }
    }
}