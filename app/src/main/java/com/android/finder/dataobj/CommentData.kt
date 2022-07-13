package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class CommentData(
    @SerializedName("answerId") val answerId: Long,
    @SerializedName("answerContent") val answerContent: String,
    @SerializedName("userId") val userId: Long,
    @SerializedName("userMBTI") val userMBTI: String,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("createTime") val createTime: String,
    @SerializedName("replyHistDtos") val replyList : List<ReCommentData>
)
