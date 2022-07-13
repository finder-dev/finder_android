package com.android.finder.network.response

import com.android.finder.dataobj.CommunityHotTitleData

data class CommunityHotResponse(
    val response: List<CommunityHotTitleData>,
    val success: Boolean
)