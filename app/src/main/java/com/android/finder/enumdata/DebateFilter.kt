package com.android.finder.enumdata

enum class DebateFilter {
    PROCEEDING,
    COMPLETE;

    val filterString : String
        get() {
            return when(this) {
                PROCEEDING -> "PROCEEDING"
                COMPLETE -> "COMPLETE"
            }
        }
}