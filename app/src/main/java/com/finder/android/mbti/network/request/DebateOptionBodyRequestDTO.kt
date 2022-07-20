package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class DebateOptionBodyRequestDTO(
    @SerializedName("option") val option : String
)
