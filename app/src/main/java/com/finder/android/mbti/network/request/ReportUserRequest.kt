package com.finder.android.mbti.network.request

import com.google.gson.annotations.SerializedName

data class ReportUserRequest(
    @SerializedName("reportUserId") val reportUserId : Long
)
