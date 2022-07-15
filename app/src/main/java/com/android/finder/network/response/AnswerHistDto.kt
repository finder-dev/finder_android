package com.android.finder.network.response

import com.android.finder.dataobj.ReCommentData
import com.google.gson.annotations.SerializedName

data class AnswerHistDto(
    @SerializedName("createTime") val createTime: String,
    @SerializedName("debateAnswerContent") val answerContent: String,
    @SerializedName("debateAnswerId") val answerId: Long,
    @SerializedName("replyHistDtos") val replyList: List<ReCommentData>,
    @SerializedName("userId") val userId: Long,
    @SerializedName("userMBTI") val userMBTI: String,
    @SerializedName("userNickname") val userNickname: String
)