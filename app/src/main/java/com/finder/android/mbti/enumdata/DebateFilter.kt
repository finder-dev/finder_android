package com.finder.android.mbti.enumdata

import com.finder.android.mbti.R
import com.finder.android.mbti.App

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