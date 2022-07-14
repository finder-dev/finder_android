package com.android.finder.network.response

import com.google.gson.annotations.SerializedName

data class EmailCodeCheckResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("response") val response : SuccessMessageResponse?,
    @SerializedName("errorResponse") val errorResponse: ErrorResponse
)