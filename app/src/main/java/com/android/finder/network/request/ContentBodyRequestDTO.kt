package com.android.finder.network.request

import com.google.gson.annotations.SerializedName

data class ContentBodyRequestDTO(
    @SerializedName("content") val content : String
)
