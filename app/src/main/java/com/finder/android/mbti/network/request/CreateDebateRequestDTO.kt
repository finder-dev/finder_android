package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class CreateDebateRequestDTO(
    @SerializedName("title") val title : String,
    @SerializedName("optionA") val optionA : String,
    @SerializedName("optionB") val optionB : String
)