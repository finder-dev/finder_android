package com.android.finder.enum

import com.android.finder.App
import com.android.finder.R

enum class HomeBottomTap {
    Home,
    DEBATE;

    val iconResourceId: Int
    get() {
        return when(this) {
            Home -> R.drawable.ic_selector_home
            DEBATE -> R.drawable.ic_selector_debate
        }
    }

    val tapName : String
    get() {
        return when(this) {
            Home -> App.instance.resources.getString(R.string.home)
            DEBATE -> App.instance.resources.getString(R.string.debate)
        }
    }
}