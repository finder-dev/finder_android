package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class ReCommentData(
    @SerializedName("replyId") val id : Long,
    @SerializedName("replyContent") val replyContent : String,
    @SerializedName("userId") val userId : Long,
    @SerializedName("createTime") val createTime: String,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("userMBTI") val userMBTI : String
)
