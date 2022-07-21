package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class SendANoteRequestDTO(
    @SerializedName("toUserId") val toUserId : Long,
    @SerializedName("content") val content : String
)