package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName

data class DebateListResponseDTO(
    @SerializedName("response") val debateResponse: DebateResponse,
    @SerializedName("success") val success: Boolean
)