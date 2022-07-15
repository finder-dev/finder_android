package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class DebateListVO(
    @SerializedName("debateId") val debateId : Long,
    @SerializedName("title") val title : String,
    @SerializedName("joinCount") val joinCount : Int,
    @SerializedName("debateState") val debateState : String,
    @SerializedName("deadline") val deadline : String
)