package com.android.finder.network.response

import com.android.finder.dataobj.DebateHotVO
import com.google.gson.annotations.SerializedName

data class DebateHotResponse(
    @SerializedName("response") val response: DebateHotVO,
    @SerializedName("success") val success: Boolean
)