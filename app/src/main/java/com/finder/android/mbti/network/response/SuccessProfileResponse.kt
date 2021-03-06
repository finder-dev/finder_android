package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.UserProfile
import com.google.gson.annotations.SerializedName

data class SuccessProfileResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("response") val response : UserProfile?,
    @SerializedName("errorResponse") val errorResponse: ErrorResponse
)
