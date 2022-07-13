package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("userId") val userId: Long,
    @SerializedName("email") val email : String,
    @SerializedName("mbti") val mbti : String,
    @SerializedName("nickname") val nickname: String
)
