package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName

data class NoteListResponseDTO(
    @SerializedName("response") val response: NoteListResponse,
    @SerializedName("success") val success: Boolean
)
