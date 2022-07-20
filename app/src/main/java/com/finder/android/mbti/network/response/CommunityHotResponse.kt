package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.CommunityHotTitleData
import com.google.gson.annotations.SerializedName

data class CommunityHotResponse(
    @SerializedName("response") val response: List<CommunityHotTitleData>,
    @SerializedName("success") val success: Boolean
)