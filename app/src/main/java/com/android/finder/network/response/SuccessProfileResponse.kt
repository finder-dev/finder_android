package com.android.finder.network.response

import com.android.finder.dataobj.UserProfile
import com.google.gson.annotations.SerializedName

data class SuccessProfileResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("response") val response : UserProfile?,
    @SerializedName("errorResponse") val errorResponse: ErrorResponse
)
