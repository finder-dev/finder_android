package com.finder.android.mbti.dataobj

import com.google.gson.annotations.SerializedName

data class NoteWithUserVO(
    @SerializedName("toUserId") val toUserId : Long,
    @SerializedName("fromUserId") val fromUserId : Long,
    @SerializedName("content") val content : String,
    @SerializedName("createTime") val createTime : String
)