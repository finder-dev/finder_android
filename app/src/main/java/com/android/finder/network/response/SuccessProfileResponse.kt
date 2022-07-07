package com.android.finder.network.response

import com.android.finder.dataobj.UserProfile

data class SuccessProfileResponse(
    val success : Boolean,
    val response : UserProfile?,
    val errorResponse: ErrorResponse
)
