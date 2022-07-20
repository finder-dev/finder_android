package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName

data class DebateDetailResponseDTO(
    @SerializedName("response") val response: DebateDetailResponseVO,
    @SerializedName("success") val success: Boolean
)