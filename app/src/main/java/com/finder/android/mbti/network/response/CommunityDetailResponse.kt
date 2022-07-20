package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.CommunityDetailDto
import com.google.gson.annotations.SerializedName

data class CommunityDetailResponse(
    @SerializedName("response") val response: CommunityDetailDto,
    @SerializedName("success") val success: Boolean
)