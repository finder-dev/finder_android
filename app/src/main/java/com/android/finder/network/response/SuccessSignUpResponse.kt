package com.android.finder.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class SuccessSignUpResponse(
    @SerializedName("grantType") val grantType : String,
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("accessTokenExpireTime") val accessTokenExpireTime : String,
    @SerializedName("refreshToken") val refreshToken : String,
    @SerializedName("refreshTokenExpireTime") val refreshTokenExpireTime: Date
)