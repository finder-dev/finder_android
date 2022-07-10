package com.android.finder.component

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.finder.dpToPx

class RecyclerViewGridItemDeco(
    private val context: Context,
    private val bottomMargin: Int = 0,
    private val topMargin: Int = 0,
    private val rightMargin: Int = 0,
    private val leftMargin: Int = 0,
    private val gridCount: Int = 1
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val gridLayout = view.layoutParams as GridLayoutManager.LayoutParams
        val lastIndex : Int = if(state.itemCount % gridCount ==0) state.itemCount - gridCount
        else state.itemCount - state.itemCount % gridCount
        when (parent.getChildAdapterPosition(view)) {
            in 0 until gridCount -> outRect.bottom = bottomMargin.dpToPx(context)
            in lastIndex until state.itemCount -> outRect.top = topMargin.dpToPx(context)
            else -> {
                outRect.top = topMargin.dpToPx(context)
                outRect.bottom = bottomMargin.dpToPx(context)
            }
        }
        when (gridLayout.spanIndex) {
            0 -> outRect.right = rightMargin.dpToPx(context)
            gridCount - 1 -> outRect.left = leftMargin.dpToPx(context)
            else -> {
                outRect.right = rightMargin.dpToPx(context)
                outRect.left = leftMargin.dpToPx(context)
            }
        }
    }
}