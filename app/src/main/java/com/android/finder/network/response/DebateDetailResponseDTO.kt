package com.android.finder.network.response

import com.google.gson.annotations.SerializedName

data class DebateDetailResponseDTO(
    @SerializedName("response") val response: DebateDetailResponseVO,
    @SerializedName("success") val success: Boolean
)