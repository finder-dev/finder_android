package com.finder.android.mbti.network.response

import com.finder.android.mbti.dataobj.CommentData
import com.google.gson.annotations.SerializedName

data class DebateDetailResponseVO(
    @SerializedName("answerCount") val answerCount: Int,
    @SerializedName("answerHistDtos") val commentList: List<CommentData>,
    @SerializedName("deadline") val deadline: String,
    @SerializedName("debateId") val debateId: Long,
    @SerializedName("debateTitle") val debateTitle: String,
    @SerializedName("optionA") val optionA: String,
    @SerializedName("optionACount") val optionACount: Int,
    @SerializedName("optionB") val optionB: String,
    @SerializedName("optionBCount") val optionBCount: Int,
    @SerializedName("writerId") val writerId: Long,
    @SerializedName("writerMBTI") val writerMBTI: String,
    @SerializedName("writerNickname") val writerNickname: String,
    @SerializedName("join") val join: Boolean,
    @SerializedName("joinOption") val joinOption: String?,
)