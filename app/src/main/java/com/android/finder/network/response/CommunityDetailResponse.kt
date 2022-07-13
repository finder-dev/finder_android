package com.android.finder.network.response

import com.android.finder.dataobj.CommunityDetailDto

data class CommunityDetailResponse(
    val response: CommunityDetailDto,
    val success: Boolean
)