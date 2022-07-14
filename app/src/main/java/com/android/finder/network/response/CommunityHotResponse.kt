package com.android.finder.network.response

import com.android.finder.dataobj.CommunityHotTitleData
import com.google.gson.annotations.SerializedName

data class CommunityHotResponse(
    @SerializedName("response") val response: List<CommunityHotTitleData>,
    @SerializedName("success") val success: Boolean
)