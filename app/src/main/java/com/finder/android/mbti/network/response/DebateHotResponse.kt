package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.DebateHotVO
import com.google.gson.annotations.SerializedName

data class DebateHotResponse(
    @SerializedName("response") val response: DebateHotVO,
    @SerializedName("success") val success: Boolean
)