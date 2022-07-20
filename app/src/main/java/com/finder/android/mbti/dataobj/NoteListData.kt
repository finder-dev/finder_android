package com.finder.android.mbti.dataobj

import com.google.gson.annotations.SerializedName

data class NoteListData(
    @SerializedName("toUserId") val targetUserId: Long,
    @SerializedName("userNickname") val userNickname : String,
    @SerializedName("content") val content: String,
    @SerializedName("createTime") val createTime : String
)
