package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("response") val response : SuccessMessageResponse?,
    @SerializedName("errorResponse") val errorResponse: ErrorResponse
)
