package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName

data class NoteListWithUserResponseDTO(
    @SerializedName("success") val success: Boolean,
    @SerializedName("response") val response : NoteListWithUserResponse
)
