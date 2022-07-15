package com.android.finder.dataobj

data class DebateHotVO(
    val deadline: String,
    val debateId: Int,
    val join: Boolean,
    val joinOption: String?,
    val optionA: String,
    val optionACount: Int,
    val optionB: String,
    val optionBCount: Int,
    val title: String
)