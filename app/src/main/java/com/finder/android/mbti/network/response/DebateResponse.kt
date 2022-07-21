package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.DebateListVO
import com.google.gson.annotations.SerializedName

data class DebateResponse(
    @SerializedName("content") val content: List<DebateListVO>,
    @SerializedName("last") val last: Boolean
)