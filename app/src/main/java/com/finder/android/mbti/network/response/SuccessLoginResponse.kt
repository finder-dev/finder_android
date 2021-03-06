package com.finder.android.mbti.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class SuccessLoginResponse(
    @SerializedName("grantType") val grantType : String,
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("accessTokenExpireTime") val accessTokenExpireTime : String,
    @SerializedName("refreshToken") val refreshToken : String,
    @SerializedName("refreshTokenExpireTime") val refreshTokenExpireTime: Date
)