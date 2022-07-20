package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class ContentBodyRequestDTO(
    @SerializedName("content") val content : String
)
