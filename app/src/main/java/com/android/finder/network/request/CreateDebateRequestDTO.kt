package com.android.finder.network.request

import com.google.gson.annotations.SerializedName

data class CreateDebateRequestDTO(
    @SerializedName("title") val title : String,
    @SerializedName("optionA") val optionA : String,
    @SerializedName("optionB") val optionB : String
)