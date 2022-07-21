package com.finder.android.mbti.dataobj

import com.google.gson.annotations.SerializedName

data class NoteWithUserVO(
    @SerializedName("ownerId") val toUserId : Long,
    @SerializedName("otherId") val fromUserId : Long,
    @SerializedName("content") val content : String,
    @SerializedName("createTime") val createTime : String
)