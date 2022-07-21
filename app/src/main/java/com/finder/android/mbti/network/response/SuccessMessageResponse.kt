package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName

data class SuccessMessageResponse(
    @SerializedName("message") val message : String?
)