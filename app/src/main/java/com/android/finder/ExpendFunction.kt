package com.android.finder

import android.widget.TextView

fun TextView.setTextColorResource(colorResource : Int) {
    this.setTextColor(resources.getColor(colorResource, null))
}