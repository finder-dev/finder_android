package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class DebateHotVO(
    @SerializedName("deadline") val deadline: String,
    @SerializedName("debateId") val debateId: Long,
    @SerializedName("join") val join: Boolean,
    @SerializedName("joinOption") val joinOption: String?,
    @SerializedName("optionA") val optionA: String,
    @SerializedName("optionACount") val optionACount: Int,
    @SerializedName("optionB") val optionB: String,
    @SerializedName("optionBCount") val optionBCount: Int,
    @SerializedName("title") val title: String
)