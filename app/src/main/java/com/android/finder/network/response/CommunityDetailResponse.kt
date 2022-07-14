package com.android.finder.network.response

import com.android.finder.dataobj.CommunityDetailDto
import com.google.gson.annotations.SerializedName

data class CommunityDetailResponse(
    @SerializedName("response") val response: CommunityDetailDto,
    @SerializedName("success") val success: Boolean
)