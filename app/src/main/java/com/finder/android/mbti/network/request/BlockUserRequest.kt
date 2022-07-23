package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class BlockUserRequest(
    @SerializedName("blockUserId") val userId : Long
)
