package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class DeleteUserRequestDTO(
    @SerializedName("deleteMsgUserId") val deleteUserId : Long
)
