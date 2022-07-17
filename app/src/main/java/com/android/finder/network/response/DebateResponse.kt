package com.android.finder.network.response

import com.android.finder.dataobj.DebateListVO
import com.google.gson.annotations.SerializedName

data class DebateResponse(
    @SerializedName("content") val content: List<DebateListVO>,
    @SerializedName("last") val last: Boolean
)