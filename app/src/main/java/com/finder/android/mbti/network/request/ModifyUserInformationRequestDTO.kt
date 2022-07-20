package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class ModifyUserInformationRequestDTO(
    @SerializedName("nickname") val nickname : String,
    @SerializedName("mbti") val mbti : String,
    @SerializedName("password") val password : String? = null
)