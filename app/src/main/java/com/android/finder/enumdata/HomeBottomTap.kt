package com.android.finder.enumdata

import com.android.finder.App
import com.android.finder.R

enum class HomeBottomTap {
    Home,
    DEBATE,
    COMMUNITY,
//    SAVE,
    MY;

    val iconResourceId: Int
    get() {
        return when(this) {
            Home -> R.drawable.ic_selector_home
            DEBATE -> R.drawable.ic_selector_debate
            COMMUNITY -> R.drawable.ic_selector_community
//            SAVE -> R.drawable.ic_selector_save
            MY -> R.drawable.ic_selector_my
        }
    }

    val tapName : String
    get() {
        return when(this) {
            Home -> App.instance.resources.getString(R.string.home)
            DEBATE -> App.instance.resources.getString(R.string.debate)
            COMMUNITY -> App.instance.resources.getString(R.string.community)
//            SAVE -> App.instance.getString(R.string.save)
            MY -> App.instance.resources.getString(R.string.my)
        }
    }
}