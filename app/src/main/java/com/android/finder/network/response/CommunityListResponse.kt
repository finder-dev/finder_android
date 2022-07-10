package com.android.finder.network.response

import com.android.finder.dataobj.CommunityListDto

data class CommunityListResponse(
    val success : Boolean,
    val response : CommunityListDto?
)