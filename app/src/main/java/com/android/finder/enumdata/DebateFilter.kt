package com.android.finder.enumdata

import com.android.finder.App
import com.android.finder.R

enum class DebateFilter {
    PROCEEDING,
    COMPLETE;

    val filterString: String
        get() {
            return when (this) {
                PROCEEDING -> "PROCEEDING"
                COMPLETE -> "COMPLETE"
            }
        }

    val filterViewString: String
        get() {
            return when (this) {
                PROCEEDING -> App.instance.resources.getString(R.string.proceeding_debate)
                COMPLETE -> App.instance.resources.getString(R.string.complete_debate)
            }
        }
}