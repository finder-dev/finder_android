package com.android.finder.network.response

import com.google.gson.annotations.SerializedName

data class EmailLoginResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("response") val response : SuccessLoginResponse?,
    @SerializedName("errorResponse") val errorResponse: ErrorResponse
)


