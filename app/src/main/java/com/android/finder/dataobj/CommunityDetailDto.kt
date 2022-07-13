package com.android.finder.dataobj

data class CommunityDetailDto(
    val answerCount: Int,
    val answerHistDtos: List<CommentData>,
    val communityContent: String,
    val communityId: Long,
    val communityImgDtos: List<CommunityImgDto>,
    val communityMBTI: String,
    val communityTitle: String,
    val createTime: String,
    val isQuestion: Boolean,
    var likeCount: Int,
    var likeUser: Boolean,
    var saveUser: Boolean,
    val userId: Long,
    val userMBTI: String,
    val userNickname: String
)