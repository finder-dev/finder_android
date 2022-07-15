package com.android.finder.network.request

import com.google.gson.annotations.SerializedName

data class DebateOptionBodyRequestDTO(
    @SerializedName("option") val option : String
)
