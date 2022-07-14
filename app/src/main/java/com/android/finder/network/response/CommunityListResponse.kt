package com.android.finder.network.response

import com.android.finder.dataobj.CommunityListDto
import com.google.gson.annotations.SerializedName

data class CommunityListResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("response") val response : CommunityListDto?
)