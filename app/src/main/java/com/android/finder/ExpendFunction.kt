package com.android.finder

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView

fun TextView.setTextColorResource(colorResource : Int) {
    this.setTextColor(resources.getColor(colorResource, null))
}

fun ImageView.imageSrcCompatResource(resourceId: Int) {
    setImageResource(resourceId)
}

fun Int.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}