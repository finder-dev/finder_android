package com.android.finder.dataobj

import com.google.gson.annotations.SerializedName

data class CommunityDetailDto(
    @SerializedName("answerCount") val answerCount: Int,
    @SerializedName("answerHistDtos") val answerHistDtos: List<CommentData>,
    @SerializedName("communityContent") val communityContent: String,
    @SerializedName("communityId") val communityId: Long,
    @SerializedName("communityImgDtos") val communityImgDtos: List<CommunityImgDto>,
    @SerializedName("communityMBTI") val communityMBTI: String,
    @SerializedName("communityTitle") val communityTitle: String,
    @SerializedName("createTime") val createTime: String,
    @SerializedName("isQuestion") val isQuestion: Boolean,
    @SerializedName("likeCount") var likeCount: Int,
    @SerializedName("likeUser") var likeUser: Boolean,
    @SerializedName("saveUser") var saveUser: Boolean,
    @SerializedName("userId") val userId: Long,
    @SerializedName("userMBTI") val userMBTI: String,
    @SerializedName("userNickname") val userNickname: String
)