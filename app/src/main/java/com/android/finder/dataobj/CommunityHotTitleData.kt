package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class CommunityHotTitleData(
    @SerializedName("communityId") val communityId : Long,
    @SerializedName("title") val title : String
)
