package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.CommunityListDto
import com.google.gson.annotations.SerializedName

data class CommunityListResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("response") val response : CommunityListDto?
)