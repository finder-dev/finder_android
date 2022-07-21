package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("errorMessages") val errorMessages: List<String>,
    @SerializedName("status") val status: Int
)